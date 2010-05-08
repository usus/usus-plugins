// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.modelupdate.checkpoints;

import static org.eclipse.core.runtime.Status.OK_STATUS;
import static org.projectusus.core.internal.proportions.modelupdate.checkpoints.XmlNames.CHECKPOINTS_FILE;
import static org.projectusus.core.internal.util.CoreTexts.saveCheckpointsJob_name;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;
import org.projectusus.core.basis.ICheckpoint;
import org.projectusus.core.internal.UsusCorePlugin;

public class SaveCheckpointsJob extends Job {

    private final List<ICheckpoint> checkpoints;

    public SaveCheckpointsJob( List<ICheckpoint> checkpoints ) {
        super( saveCheckpointsJob_name );
        this.checkpoints = checkpoints;
        setPriority( DECORATE );
    }

    /**
     * @param monitor
     *            is not used
     */
    @Override
    protected IStatus run( IProgressMonitor monitor ) {
        try {
            IPath stateLocation = getStateLocation().append( CHECKPOINTS_FILE );
            FileWriter writer = new FileWriter( stateLocation.toOSString() );
            writer.write( generateFileContent() );
            writer.close();
        } catch( IOException ioex ) {
            // not directly displayed to the user, but into the error log
            String msg = "Unable to save code proportions checkpoints."; //$NON-NLS-1$
            UsusCorePlugin.log( msg, ioex );
        }
        return OK_STATUS;
    }

    // internal
    // ////////

    private String generateFileContent() {
        return new CheckpointWriter( checkpoints ).toXml();
    }

    private IPath getStateLocation() {
        return UsusCorePlugin.getDefault().getStateLocation();
    }
}
