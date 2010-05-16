package org.projectusus.core.internal.proportions.rawdata;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.projectusus.core.internal.proportions.IUsusModelForAdapter;

@SuppressWarnings( "unused" )
public class NullUsusModelForAdapter implements IUsusModelForAdapter {

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
