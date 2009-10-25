// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.projectusus.core.internal.UsusCorePlugin;

class RunComputationOnResourceChange implements IResourceChangeListener {

    public void resourceChanged( IResourceChangeEvent event ) {
        IResourceDelta delta = event.getDelta();
        if( delta != null ) {
            try {
                runComputationJob( delta );
            } catch( CoreException cex ) {
                log( cex.getStatus() );
            }
        }
    }

    private void runComputationJob( IResourceDelta delta ) throws CoreException {
        ICodeProportionComputationTarget target = new DeltaCodeProportionComputationTarget( delta );
        new CodeProportionsComputerJob( target ).schedule();
    }

    private void log( IStatus status ) {
        UsusCorePlugin.getDefault().getLog().log( status );
    }
}
