// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageDeclaration;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.projectusus.core.basis.CodeProportionKind;
import org.projectusus.core.basis.IFileRawData;
import org.projectusus.core.basis.IHotspot;
import org.projectusus.core.filerelations.model.BoundType;
import org.projectusus.core.filerelations.model.Classname;
import org.projectusus.core.internal.proportions.model.Hotspot;
import org.projectusus.core.internal.proportions.rawdata.jdtdriver.ASTSupport;

public class FileRawData extends RawData<Integer, ClassRawData> implements IFileRawData {

    private final IFile fileOfRawData;

    private WarningsCount yellowCount = new WarningsCount();

    public FileRawData( IFile file ) {
        super(); // sagt AL ;)
        this.fileOfRawData = file;
    }

    public IFile getFileOfRawData() {
        return fileOfRawData;
    }

    @Override
    public String toString() {
        return "Data for " + fileOfRawData.getFullPath() + ", " + getNumberOfClasses() + " classes"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    public void setCCValue( MethodDeclaration methodDecl, int value ) {
        getRawData( methodDecl ).setCCValue( methodDecl, value );
    }

    public void setCCValue( Initializer initializer, int value ) {
        getRawData( initializer ).setCCValue( initializer, value );
    }

    public void setMLValue( MethodDeclaration methodDecl, int value ) {
        getRawData( methodDecl ).setMLValue( methodDecl, value );
    }

    public void setMLValue( Initializer initializer, int value ) {
        getRawData( initializer ).setMLValue( initializer, value );
    }

    public void setYellowCount( int count ) {
        yellowCount.setWarningsCount( count );
    }

    public void addClass( AbstractTypeDeclaration node ) {
        getClassRawData( node );
    }

    public ClassRawData getClassRawData( AbstractTypeDeclaration node ) {
        if( node == null ) {
            return null;
        }
        return getRawData( BoundType.of( node ), node.getStartPosition(), JDTSupport.calcLineNumber( node ), node.getName().toString() );
    }

    public int getNumberOfClasses() {
        return getRawDataElementCount();
    }

    @Override
    public void addToHotspots( CodeProportionKind metric, List<IHotspot> hotspots ) {
        List<IHotspot> localHotspots = getHotspotsForThisFile( metric );
        addFileInfoToHotspots( localHotspots );
        hotspots.addAll( localHotspots );
    }

    private void addFileInfoToHotspots( List<IHotspot> localHotspots ) {
        for( IHotspot hotspot : localHotspots ) {
            ((Hotspot)hotspot).setFile( fileOfRawData );
        }
    }

    private List<IHotspot> getHotspotsForThisFile( CodeProportionKind metric ) {
        List<IHotspot> localHotspots = new ArrayList<IHotspot>();
        if( metric == CodeProportionKind.CW && metric.isViolatedBy( this ) ) {
            localHotspots.add( yellowCount.createHotspot() );
        } else {
            super.addToHotspots( metric, localHotspots );
        }
        return localHotspots;
    }

    private ClassRawData getRawData( BoundType typeBinding, int start, int lineNumber, String name ) {
        Integer startObject = new Integer( start );
        ClassRawData rawData = super.getRawData( startObject );
        if( rawData == null ) {
            rawData = new ClassRawData( typeBinding, name, start, lineNumber );
            super.addRawData( startObject, rawData );
        }
        return rawData;
    }

    private ClassRawData getRawData( MethodDeclaration node ) {
        return getClassRawData( ASTSupport.findEnclosingClass( node ) );
    }

    private ClassRawData getRawData( Initializer node ) {
        return getClassRawData( ASTSupport.findEnclosingClass( node ) );
    }

    public ClassRawData getOrCreateRawData( IJavaElement element ) {
        try {
            ICompilationUnit compilationUnit = getCompilationUnit( element );
            if( compilationUnit == null ) {
                return null;
            }

            ClassRawData rawData = getClassRawData( element, compilationUnit );
            if( rawData == null ) {
                rawData = createClassRawDataFor( element, compilationUnit.getPackageDeclarations() );
            }
            return rawData;
        } catch( JavaModelException e ) {
            return null;
        }
    }

    public ClassRawData getRawData( IJavaElement element ) {
        // TODO nr Duplicate code from getOrCreateRawData
        ICompilationUnit compilationUnit = getCompilationUnit( element );
        if( compilationUnit == null ) {
            return null;
        }

        try {
            return getClassRawData( element, compilationUnit );
        } catch( JavaModelException e ) {
            return null;
        }
    }

    private ICompilationUnit getCompilationUnit( IJavaElement element ) {
        if( element == null ) {
            return null;
        }
        return JDTSupport.getCompilationUnit( element );
    }

    private ClassRawData getClassRawData( IJavaElement element, ICompilationUnit compilationUnit ) throws JavaModelException {
        for( Integer startPosition : getAllKeys() ) {
            IJavaElement foundElement = compilationUnit.getElementAt( startPosition.intValue() );
            if( element.equals( foundElement ) ) {
                return super.getRawData( startPosition );
            }
        }
        return null;
    }

    private ClassRawData createClassRawDataFor( IJavaElement element, IPackageDeclaration[] packageDeclarations ) {
        if( element instanceof IType ) {
            String name = element.getElementName();
            IType type = (IType)element;
            try {
                int lineNumber = JDTSupport.calcLineNumber( type );
                int startPosition = type.getSourceRange().getOffset();
                String packageName = packageDeclarations[0].getElementName();
                ClassRawData rawData = new ClassRawData( fileOfRawData, packageName, name, startPosition, lineNumber );
                super.addRawData( new Integer( startPosition ), rawData );
                return rawData;
            } catch( JavaModelException e ) {
                // do nothing
            }
        }
        return null;
    }

    @Override
    public int getViolationCount( CodeProportionKind metric ) {
        if( metric == CodeProportionKind.CW ) {
            return yellowCount.getViolationCount( metric );
        }
        return super.getViolationCount( metric );
    }

    @Override
    public synchronized int getOverallMetric( CodeProportionKind metric ) {
        if( metric == CodeProportionKind.CW ) {
            return yellowCount.getOverallMetric( metric );
        }
        return super.getOverallMetric( metric );
    }

    public ClassRawData findClass( Classname classname ) {
        for( ClassRawData classRD : getAllRawDataElements() ) {
            if( classRD.getClassName().equals( classname.toString() ) ) {
                return classRD;
            }
        }
        return null;
    }
}
