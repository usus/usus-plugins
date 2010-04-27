package org.projectusus.core.internal.proportions.rawdata;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.projectusus.core.internal.proportions.IUsusModelWriteAccess;

@SuppressWarnings( "unused" )
public class NullUsusModelWriteAccess implements IUsusModelWriteAccess {

    public void dropRawData( IFile file ) {
        // just do nothing harmful
    }

    public void dropRawData( IProject project ) {
        // just do nothing harmful
    }

    public void updateAfterComputationRun( boolean ok, IProgressMonitor monitor ) {
        // just do nothing harmful
    }

}
