package org.projectusus.core.util;

import org.eclipse.core.resources.IFile;

public abstract class FileSupport {

    public static boolean isJavaFile( IFile file ) {
        return file.getName().toLowerCase().endsWith( ".java" );
    }
}
