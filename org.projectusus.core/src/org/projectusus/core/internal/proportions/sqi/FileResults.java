// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.sqi;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.projectusus.core.internal.proportions.model.Hotspot;
import org.projectusus.core.internal.proportions.model.IHotspot;
import org.projectusus.core.internal.proportions.sqi.jdtdriver.ASTSupport;

public class FileResults extends Results<AbstractTypeDeclaration, ClassResults> {

    private final IFile fileOfResults;

    public FileResults( IFile file ) {
        super(); // sagt AL ;)
        this.fileOfResults = file;
    }

    public IFile getFileOfResults() {
        return fileOfResults;
    }

    public void setCCResult( MethodDeclaration methodDecl, int value ) {
        getResults( methodDecl ).setCCResult( methodDecl, value );
    }

    public void setMLResult( MethodDeclaration methodDecl, int value ) {
        getResults( methodDecl ).setMLResult( methodDecl, value );
    }

    public void addClass( AbstractTypeDeclaration node ) {
        getResults( node );
    }

    public int getNumberOfClasses() {
        return getResultCount();
    }

    @Override
    public void addHotspots( IsisMetrics metric, List<IHotspot> hotspots ) {
        List<IHotspot> localHotspots = new ArrayList<IHotspot>();
        super.addHotspots( metric, localHotspots );
        for( IHotspot hotspot : localHotspots ) {
            ((Hotspot)hotspot).setFile( fileOfResults );
        }
        hotspots.addAll( localHotspots );
    }

    private ClassResults getResults( AbstractTypeDeclaration node ) {
        return getResults( node, new ClassResults( node ) );
    }

    private ClassResults getResults( MethodDeclaration node ) {
        return getResults( ASTSupport.findEnclosingClass( node ) );
    }

    public ClassResults getResults( IJavaElement element ) {
        if( element == null ) {
            return null;
        }
        ICompilationUnit compilationUnit = JDTSupport.getCompilationUnit( element );
        if( compilationUnit == null ) {
            return null;
        }

        try {
            for( AbstractTypeDeclaration typeDecl : getAllKeys() ) {
                IJavaElement foundElement = compilationUnit.getElementAt( typeDecl.getStartPosition() );
                if( element.equals( foundElement ) ) {
                    return getResults( typeDecl );
                }
            }
        } catch( JavaModelException e ) {
            return null;
        }
        return null;
    }
}
