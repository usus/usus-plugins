// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.selection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.projectusus.core.internal.proportions.rawdata.IFileRawData;
import org.projectusus.core.internal.proportions.rawdata.IProjectRawData;

public class ExtractFileRawData {

    private final IFileRawData fileRawData;

    public ExtractFileRawData( IResource resource ) {
        fileRawData = resource == null ? null : findFileInfo( resource );
    }

    public IFileRawData getFileRawData() {
        return fileRawData;
    }

    public boolean isDataAvailable() {
        return fileRawData != null;
    }

    // internal
    // ////////

    private IFileRawData findFileInfo( IResource resource ) {
        IFileRawData result = null;
        IProjectRawData projectRawData = getProjectRawData( resource );
        if( projectRawData != null && resource instanceof IFile ) {
            result = projectRawData.getFileRawData( (IFile)resource );
        }
        return result;
    }

    private IProjectRawData getProjectRawData( IResource resource ) {
        return (IProjectRawData)resource.getProject().getAdapter( IProjectRawData.class );
    }
}
