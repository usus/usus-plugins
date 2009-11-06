package org.projectusus.core.internal.proportions.sqi;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IOpenable;

public abstract class JDTSupport {

    public static ICompilationUnit getCompilationUnit( IJavaElement element ) {
        IOpenable openable = element.getOpenable();
        if( !(openable instanceof ICompilationUnit) ) {
            return null;
        }
        return (ICompilationUnit)openable;
    }

}
