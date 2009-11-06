// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.sqi;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
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
            hotspot.setFile( fileOfResults );
        }
        hotspots.addAll( localHotspots );
    }

    private ClassResults getResults( AbstractTypeDeclaration node ) {
        return getResults( node, new ClassResults( node ) );
    }

    private ClassResults getResults( MethodDeclaration node ) {
        return getResults( ASTSupport.findEnclosingClass( node ) );
    }
}
