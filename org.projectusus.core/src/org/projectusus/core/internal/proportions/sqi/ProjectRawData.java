// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.sqi;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

public class ProjectRawData extends RawData<IFile, FileRawData> {

    private final IProject projectOfResults;
    private IFile currentFile;

    public ProjectRawData( IProject project ) {
        this.projectOfResults = project;
    }

    public IProject getProjectOfResults() {
        return projectOfResults;
    }

    public FileRawData getFileResults( IFile file ) {
        return getResults( file, new FileRawData( file ) );
    }

    public void setCurrentFile( IFile currentFile ) {
        this.currentFile = currentFile;
    }

    // for debugging!!
    public IFile getCurrentFile() {
        return currentFile;
    }

    public FileRawData getCurrentFileResults() {
        return getFileResults( currentFile );
    }

    public void dropResults( IFile file ) {
        remove( file );
    }

}
