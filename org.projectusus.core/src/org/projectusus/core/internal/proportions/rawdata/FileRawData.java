// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.projectusus.core.internal.proportions.model.Hotspot;
import org.projectusus.core.internal.proportions.model.IHotspot;
import org.projectusus.core.internal.proportions.rawdata.jdtdriver.ASTSupport;

public class FileRawData extends RawData<Integer, ClassRawData> implements IFileRawData {

    private final IFile fileOfRawData;

    public FileRawData( IFile file ) {
        super(); // sagt AL ;)
        this.fileOfRawData = file;
    }

    public IFile getFileOfRawData() {
        return fileOfRawData;
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

    public void addClass( AbstractTypeDeclaration node ) {
        getClassRawData( node );
    }

    public ClassRawData getClassRawData( AbstractTypeDeclaration node ) {
        if( node == null ) {
            return null;
        }
        return getRawData( node.getStartPosition(), JDTSupport.calcLineNumber( node ), node.getName().toString() );
    }

    public int getNumberOfClasses() {
        return getRawDataElementCount();
    }

    @Override
    public void addToHotspots( CodeProportionKind metric, List<IHotspot> hotspots ) {
        List<IHotspot> localHotspots = new ArrayList<IHotspot>();
        super.addToHotspots( metric, localHotspots );
        for( IHotspot hotspot : localHotspots ) {
            ((Hotspot)hotspot).setFile( fileOfRawData );
        }
        hotspots.addAll( localHotspots );
    }

    private ClassRawData getRawData( int start, int line, String name ) {
        Integer startObject = new Integer( start );
        ClassRawData rawData = super.getRawData( startObject );
        if( rawData == null ) {
            rawData = new ClassRawData( name, start, line );
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

    public IClassRawData getRawData( IJavaElement element ) {
        if( element == null ) {
            return null;
        }
        ICompilationUnit compilationUnit = JDTSupport.getCompilationUnit( element );
        if( compilationUnit == null ) {
            return null;
        }

        try {
            for( Integer startPosition : getAllKeys() ) {
                IJavaElement foundElement = compilationUnit.getElementAt( startPosition.intValue() );
                if( element.equals( foundElement ) ) {
                    return getRawData( startPosition.intValue(), 0, "" ); //$NON-NLS-1$ 
                }
            }
        } catch( JavaModelException e ) {
            return null;
        }
        return null;
    }

    public void addClassReference( AbstractTypeDeclaration referencingType, IJavaElement referencedElement ) {
        IResource resource = referencedElement.getResource();
        if( !(resource instanceof IFile) ) {
            return;
        }
        ClassRawData referencingRawData = getClassRawData( referencingType );
        IProjectRawData results = (IProjectRawData)resource.getProject().getAdapter( IProjectRawData.class );
        ClassRawData referencedRawData = (ClassRawData)results.getFileRawData( (IFile)resource ).getRawData( referencedElement );
        referencingRawData.addReferencedType( referencedRawData );
    }
}
