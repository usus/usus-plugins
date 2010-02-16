// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IOpenable;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
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

    // original author: Kent Beck http://stackoverflow.com/questions/561432/how-to-find-the-line-number-of-the-start-of-a-procedure-in-eclipse
    public static int calcLineNumber( final IType type ) throws JavaModelException {
        String source = type.getCompilationUnit().getSource();
        String sourceUpToMethod = source.substring( 0, type.getSourceRange().getOffset() );
        Pattern lineEnd = Pattern.compile( "$", Pattern.MULTILINE | Pattern.DOTALL ); //$NON-NLS-1$
        return lineEnd.split( sourceUpToMethod ).length;
    }

    private static ICompilationUnit toCompilationUnit( IOpenable openable ) {
        return openable instanceof ICompilationUnit ? (ICompilationUnit)openable : null;
    }

    public static FileRawData getFileRawDataFor( IFile file ) {
        return (FileRawData)getProjectRawDataFor( file ).getFileRawData( file );
    }

    public static IProjectRawData getProjectRawDataFor( IFile file ) {
        return (IProjectRawData)file.getProject().getAdapter( IProjectRawData.class );
    }
}
