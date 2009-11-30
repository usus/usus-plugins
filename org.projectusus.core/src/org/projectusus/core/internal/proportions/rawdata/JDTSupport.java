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
