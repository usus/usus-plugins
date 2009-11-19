// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.hover;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.projectusus.core.internal.project.IUSUSProject;
import org.projectusus.core.internal.proportions.sqi.FileRawData;

class ExtractRawData {

    private final FileRawData fileRawData;

    ExtractRawData( IResource resource ) {
        fileRawData = findFileInfo( resource );
    }

    FileRawData getFileRawData() {
        return fileRawData;
    }

    boolean isDataAvailable() {
        return fileRawData != null;
    }

    private FileRawData findFileInfo( IResource resource ) {
        FileRawData result = null;
        IUSUSProject ususProject = getUsusProject( resource );
        if( ususProject != null && resource instanceof IFile ) {
            result = ususProject.getProjectResults().getFileRawData( (IFile)resource );
        }
        return result;
    }

    private IUSUSProject getUsusProject( IResource resource ) {
        return (IUSUSProject)resource.getProject().getAdapter( IUSUSProject.class );
    }
}
