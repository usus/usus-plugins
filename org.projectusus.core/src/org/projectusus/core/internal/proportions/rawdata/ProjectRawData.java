// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import org.eclipse.core.resources.IFile;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.statistics.IMetricsResultVisitor;

class ProjectRawData extends RawData<IFile, FileRawData> {

    MetricsResults data;

    public ProjectRawData() {
        data = new MetricsResults();
    }

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

    public void acceptAndGuide( IMetricsResultVisitor visitor ) {
        visitor.inspectProject( data );
        JavaModelPath path = visitor.getPath();
        if( path.isRestrictedToFile() ) {
            this.getFileRawData( path.getFile() ).acceptAndGuide( visitor );
        } else {
            for( FileRawData rawData : getAllRawDataElements() ) {
                rawData.acceptAndGuide( visitor );
            }
        }
    }
}
