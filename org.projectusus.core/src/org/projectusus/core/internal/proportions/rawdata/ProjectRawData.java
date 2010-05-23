// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.projectusus.core.basis.CodeProportionKind;
import org.projectusus.core.basis.CodeProportionUnit;
import org.projectusus.core.basis.IHotspot;
import org.projectusus.core.internal.coverage.TestCoverage;
import org.projectusus.core.internal.proportions.FileSupport;

import com.mountainminds.eclemma.core.analysis.IJavaElementCoverage;

class ProjectRawData extends RawData<IFile, FileRawData> {

    // private final IProject projectOfRawData;
    private IJavaElementCoverage coverage;
    private WarningsCount projectMarkers = new WarningsCount();
    private RawDataMapWrapper<IFile, MiscFileRawData> miscRawData = new RawDataMapWrapper<IFile, MiscFileRawData>();

    public FileRawData getOrCreateFileRawData( IFile file ) {
        FileRawData rawData = getFileRawData( file );
        if( rawData == null ) {
            rawData = createFileRawData( file );
        }
        return rawData;
    }

    public FileRawData getFileRawData( IFile file ) {
        return super.getRawData( file );
    }

    private FileRawData createFileRawData( IFile file ) {
        FileRawData rawData = new FileRawData( file );
        super.addRawData( file, rawData );
        return rawData;
    }

    public void dropRawData( IFile file ) {
        FileRawData fileRawData = getFileRawData( file );
        if( fileRawData != null ) {
            fileRawData.dropRawData();
        }
        remove( file );
    }

    public void dropRawData() {
        for( FileRawData fileRD : getAllRawDataElements() ) {
            fileRD.dropRawData();
        }
        removeAll();
    }

    public void setInstructionCoverage( IJavaElementCoverage coverage ) {
        this.coverage = coverage;
    }

    public TestCoverage getInstructionCoverage() {
        if( coverage != null ) {
            return new TestCoverage( coverage.getInstructionCounter() );
        }
        return new TestCoverage( 0, 0 );
    }

    public void setWarningsCount( IFile file, int markerCount ) {
        if( FileSupport.isJavaFile( file ) ) {
            getOrCreateFileRawData( file ).setYellowCount( markerCount );
        } else {
            getMiscFileRawData( file ).setYellowCount( markerCount );
        }
    }

    public void setWarningsCount( int markerCount ) {
        projectMarkers.setWarningsCount( markerCount );
    }

    private MiscFileRawData getMiscFileRawData( IFile file ) {
        MiscFileRawData rawData = miscRawData.getRawData( file );
        if( rawData == null ) {
            rawData = new MiscFileRawData( file );
            miscRawData.addRawData( file, rawData );
        }
        return rawData;
    }

    @Override
    public synchronized int getViolationCount( CodeProportionKind metric ) {
        int violationCount = super.getViolationCount( metric );
        violationCount += getViolationCountForMiscFiles( metric );
        return violationCount;
    }

    private int getViolationCountForMiscFiles( CodeProportionKind metric ) {
        if( metric == CodeProportionKind.CW ) {
            int result = 0;
            for( MiscFileRawData rawData : miscRawData.getAllRawDataElements() ) {
                result += rawData.getViolationCount( metric );
            }
            return result;
        }
        return 0;
    }

    @Override
    public int getNumberOf( CodeProportionUnit unit ) {
        if( unit == CodeProportionUnit.PROJECT ) {
            return 1;
        }
        if( unit == CodeProportionUnit.ANYFILE ) {
            return getRawDataElementCount() + miscRawData.getRawDataElementCount();
        }
        return super.getNumberOf( unit );
    }

    @Override
    public synchronized void addToHotspots( CodeProportionKind metric, List<IHotspot> hotspots ) {
        super.addToHotspots( metric, hotspots );

        if( metric == CodeProportionKind.CW ) {
            for( MiscFileRawData rawData : miscRawData.getAllRawDataElements() ) {
                rawData.addToHotspots( metric, hotspots );
            }
            // TODO project zufuegen
        }
    }

    @Override
    public synchronized int getOverallMetric( CodeProportionKind metric ) {
        if( metric == CodeProportionKind.CW ) {
            int sum = projectMarkers.getOverallMetric( metric );
            for( FileRawData rawData : getAllRawDataElements() ) {
                sum += rawData.getOverallMetric( metric );
            }
            for( MiscFileRawData rawData : miscRawData.getAllRawDataElements() ) {
                sum += rawData.getOverallMetric( metric );
            }
            return sum;
        }
        return super.getOverallMetric( metric );
    }

    public boolean hasWarnings() {
        return getViolationCount( CodeProportionKind.CW ) > 0;
    }
}
