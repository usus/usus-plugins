// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.hover;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.projectusus.core.internal.project.IUSUSProject;
import org.projectusus.core.internal.proportions.rawdata.IFileRawData;

class ExtractRawData {

    private final IFileRawData fileRawData;

    ExtractRawData( IResource resource ) {
        fileRawData = findFileInfo( resource );
    }

    IFileRawData getFileRawData() {
        return fileRawData;
    }

    boolean isDataAvailable() {
        return fileRawData != null;
    }

    private IFileRawData findFileInfo( IResource resource ) {
        IFileRawData result = null;
        IUSUSProject ususProject = getUsusProject( resource );
        if( ususProject != null && resource instanceof IFile ) {
            result = ususProject.getProjectRawData().getFileRawData( (IFile)resource );
        }
        return result;
    }

    private IUSUSProject getUsusProject( IResource resource ) {
        return (IUSUSProject)resource.getProject().getAdapter( IUSUSProject.class );
    }
}
