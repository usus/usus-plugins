// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.sqi;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.projectusus.core.internal.proportions.model.IHotspot;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class FileResults extends Results<String, ClassResults> {

    private final IFile fileOfResults;

    public FileResults( IFile file ) {
        super(); // sagt AL ;)
        this.fileOfResults = file;
    }

    public IFile getFileOfResults() {
        return fileOfResults;
    }

    public void setCCResult( BetterDetailAST methodAST, int value ) {
        getResults( methodAST ).setCCResult( methodAST, value );
    }

    public void setMLResult( BetterDetailAST methodAST, int value ) {
        getResults( methodAST ).setMLResult( methodAST, value );
    }

    public void addClass( BetterDetailAST classAST ) {
        getResults( classAST );
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

    private ClassResults getResults( BetterDetailAST methodAST ) {
        String packageName = getPackageName( methodAST );
        String className = getClassDefName( findEnclosingClass( methodAST ) );
        ClassResults classResults = getResults( className, new ClassResults( packageName, className, methodAST.getLineNo() ) );
        return classResults;
    }

    private BetterDetailAST findEnclosingClass( BetterDetailAST aAST ) {
        BetterDetailAST node = aAST;
        while( node != null && TokenTypes.CLASS_DEF != node.getType() ) {
            node = node.getParent();
        }
        return node;
    }

    private String getClassDefName( BetterDetailAST classDefNode ) {
        if( classDefNode == null ) {
            return ""; //$NON-NLS-1$
        }
        return classDefNode.findFirstToken( TokenTypes.IDENT ).getText();
    }

    private String getPackageName( BetterDetailAST classDefNode ) {
        if( classDefNode == null ) {
            return ""; //$NON-NLS-1$
        }
        BetterDetailAST packageNode = classDefNode.getPreviousSibling();
        while( packageNode != null && TokenTypes.PACKAGE_DEF != packageNode.getType() ) {
            packageNode = packageNode.getPreviousSibling();
        }

        String packageName = ""; //$NON-NLS-1$
        if( packageNode != null ) {
            BetterDetailAST dotNode = packageNode.findFirstToken( TokenTypes.DOT );
            if( dotNode != null ) {
                packageName = dotNode.getFullIdentText();
            } else {
                packageName = packageNode.findFirstToken( TokenTypes.IDENT ).getText();
            }
        }
        return packageName;
    }
}
