// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.projectusus.core.internal.proportions.model.Hotspot;
import org.projectusus.core.internal.proportions.model.IHotspot;
import org.projectusus.core.internal.proportions.sqi.jdtdriver.ASTSupport;

public class FileRawData extends RawData<Integer, ClassRawData> {

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
        getRawData( node );
    }

    private ClassRawData getRawData( AbstractTypeDeclaration node ) {
        if( node == null ) {
            return null;
        }
        ITypeBinding binding = node.resolveBinding();
        String qualifiedName = ""; //$NON-NLS-1$
        if( binding != null ) {
            qualifiedName = binding.getQualifiedName();
        }
        return getRawData( node.getStartPosition(), JDTSupport.calcLineNumber( node ), node.getName().toString(), qualifiedName );
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

    private ClassRawData getRawData( int start, int line, String name, String qualifiedName ) {
        return getRawData( new Integer( start ), new ClassRawData( name, qualifiedName, start, line ) );
    }

    private ClassRawData getRawData( MethodDeclaration node ) {
        return getRawData( ASTSupport.findEnclosingClass( node ) );
    }

    private ClassRawData getRawData( Initializer node ) {
        return getRawData( ASTSupport.findEnclosingClass( node ) );
    }

    public ClassRawData getRawData( IJavaElement element ) {
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
                    return getRawData( startPosition.intValue(), 0, "", "" ); //$NON-NLS-1$ //$NON-NLS-2$
                }
            }
        } catch( JavaModelException e ) {
            return null;
        }
        return null;
    }
}
