// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.adapter;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.projectusus.core.UsusCorePlugin;
import org.projectusus.core.statistics.UsusModelProvider;

public class RunComputationOnResourceChange implements IResourceChangeListener {

    private static final List<IResourceDelta> collectedDeltas = new ArrayList<IResourceDelta>();
    private static boolean cockpitIsVisible = false;

    public static void cockpitIsVisible() {
        cockpitIsVisible = true;
        runComputationJobOnCollectedDeltas();
    }

    public static void cockpitIsInvisible() {
        cockpitIsVisible = false;
    }

    public void resourceChanged( IResourceChangeEvent event ) {
        collectDelta( event );
        runComputationJobOnCollectedDeltas();
    }

    private void collectDelta( IResourceChangeEvent event ) {
        IResourceDelta delta = event.getDelta();
        if( delta != null ) {
            collectedDeltas.add( delta );
        }
    }

    private static void runComputationJobOnCollectedDeltas() {
        if( !cockpitIsVisible ) {
            return;
        }

        try {
            runComputationJob( collectedDeltas );
            collectedDeltas.clear();
        } catch( CoreException cex ) {
            log( cex.getStatus() );
        }
    }

    private static void runComputationJob( List<IResourceDelta> deltas ) throws CoreException {
        if( deltas.isEmpty() )
            return;

        ICodeProportionComputationTarget target = null;
        if( UsusModelProvider.ususModel().needsFullRecompute() ) {
            target = new WorkspaceCodeProportionComputationTarget();
        } else {
            target = new DeltaCodeProportionComputationTarget( deltas );
        }
        if( target.isNotEmpty() )
            new CodeProportionsComputerJob( target ).schedule();
    }

    private static void log( IStatus status ) {
        UsusCorePlugin.getDefault().getLog().log( status );
    }
}
