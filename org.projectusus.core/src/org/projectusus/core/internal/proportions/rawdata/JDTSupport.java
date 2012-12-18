// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IOpenable;

class JDTSupport {

    public static ICompilationUnit getCompilationUnit( IJavaElement element ) {
        if( element == null ) {
            return null;
        }
        return toCompilationUnit( element.getOpenable() );
    }

    private static ICompilationUnit toCompilationUnit( IOpenable openable ) {
        return openable instanceof ICompilationUnit ? (ICompilationUnit)openable : null;
    }
}
