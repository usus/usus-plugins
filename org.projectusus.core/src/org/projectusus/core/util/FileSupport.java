package org.projectusus.core.util;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.JavaCore;

public abstract class FileSupport {

    public static boolean isJavaFile( IFile file ) {
        return hasJavaLikeFileName( file ) && hasValidCompilationUnit( file );
    }

    private static boolean hasJavaLikeFileName( IFile file ) {
        return JavaCore.isJavaLikeFileName( file.getName() );
    }

    private static boolean hasValidCompilationUnit( IFile file ) {
        return JavaCore.createCompilationUnitFrom( file ).exists();
    }

}
