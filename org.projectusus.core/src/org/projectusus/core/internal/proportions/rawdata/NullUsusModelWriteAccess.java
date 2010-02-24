package org.projectusus.core.internal.proportions.rawdata;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.projectusus.core.internal.proportions.IUsusModelWriteAccess;
import org.projectusus.core.internal.proportions.modelupdate.IUsusModelUpdate;

@SuppressWarnings( "unused" )
public class NullUsusModelWriteAccess implements IUsusModelWriteAccess {

    public void dropRawData( IFile file ) {
        // just do nothing harmful
    }

    public void dropRawData( IProject project ) {
        // just do nothing harmful
    }

    public void resetRawData( IProject project ) {
        // just do nothing harmful
    }

    public void update( IUsusModelUpdate updateCommand ) {
        // just do nothing harmful
    }

}
