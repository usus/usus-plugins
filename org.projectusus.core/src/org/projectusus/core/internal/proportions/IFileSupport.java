package org.projectusus.core.internal.proportions;

import org.eclipse.core.resources.IFile;

public abstract class IFileSupport {

    public static boolean isJavaFile( IFile file ) {
        String fileExtension = file.getFileExtension();
        return fileExtension != null && fileExtension.toLowerCase().equals( "java" ); //$NON-NLS-1$
    }

}
