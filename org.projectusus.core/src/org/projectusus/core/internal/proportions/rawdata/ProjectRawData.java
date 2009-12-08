// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

public class ProjectRawData extends RawData<IFile, FileRawData> implements IProjectRawData {

    private final IProject projectOfRawData;
    private IFile currentFile;

    public ProjectRawData( IProject project ) {
        this.projectOfRawData = project;
    }

    public IFileRawData getFileRawData( IFile file ) {
        return getRawData( file, new FileRawData( file ) );
    }

    public IProject getProjectOfRawData() {
        return projectOfRawData;
    }

    public void setCurrentFile( IFile currentFile ) {
        this.currentFile = currentFile;
    }

    public FileRawData getCurrentFileRawData() {
        return getRawData( currentFile, new FileRawData( currentFile ) );
    }

    public void dropRawData( IFile file ) {
        remove( file );
    }
}
