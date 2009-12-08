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

public class JDTSupport {

    public static ICompilationUnit getCompilationUnit( IJavaElement element ) {
        return toCompilationUnit( element.getOpenable() );
    }

    public static int calcLineNumber( ASTNode node ) {
        ASTNode root = node.getRoot();
        if( root instanceof CompilationUnit ) {
            CompilationUnit cu = (CompilationUnit)root;
            return cu.getLineNumber( node.getStartPosition() );
        }
        return 0;
    }

    private static ICompilationUnit toCompilationUnit( IOpenable openable ) {
        return openable instanceof ICompilationUnit ? (ICompilationUnit)openable : null;
    }
}
