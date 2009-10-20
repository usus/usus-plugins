package org.projectusus.core.internal.proportions.sqi;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

public class ProjectResults extends Results<IFile, FileResults> {

    private final IProject projectOfResults;
    private IFile currentFile;

    public ProjectResults( IProject project ) {
        this.projectOfResults = project;
    }

    public void setCurrentFile( IFile currentFile ) {
        this.currentFile = currentFile;
    }

    public FileResults getCurrentFileResults() {
        return getResults( currentFile, new FileResults( currentFile ) );
    }

    public void dropResults( IFile file ) {
        remove( file );
    }

}
