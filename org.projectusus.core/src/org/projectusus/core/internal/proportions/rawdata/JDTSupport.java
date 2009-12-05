// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IOpenable;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;

public abstract class JDTSupport {

    public static ICompilationUnit getCompilationUnit( IJavaElement element ) {
        IOpenable openable = element.getOpenable();
        if( !(openable instanceof ICompilationUnit) ) {
            return null;
        }
        return (ICompilationUnit)openable;
    }

    public static int calcLineNumber( ASTNode node ) {
        ASTNode root = node.getRoot();
        if( root instanceof CompilationUnit ) {
            CompilationUnit cu = (CompilationUnit)root;
            return cu.getLineNumber( node.getStartPosition() );
        }
        return 0;
    }

}
