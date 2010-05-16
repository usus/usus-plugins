package org.projectusus.core.internal.proportions.rawdata.jdtdriver;

import static org.eclipse.core.resources.IMarker.PROBLEM;
import static org.eclipse.core.resources.IResource.DEPTH_ZERO;
import static org.projectusus.core.internal.UsusCorePlugin.getMetricsWriter;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.projectusus.core.internal.UsusCorePlugin;

public class ProjectDriver extends Driver {

    protected final IProject project;

    public ProjectDriver( IProject project ) {
        this.project = project;
    }

    public void compute() {
        try {
            countYellowMarkers();
        } catch( CoreException cex ) {
            UsusCorePlugin.log( cex );
        }
    }

    private void countYellowMarkers() throws CoreException {
        IMarker[] markers = project.findMarkers( PROBLEM, true, DEPTH_ZERO );
        int markerCount = getNumberOfWarnings( markers );
        getMetricsWriter().setWarningsCount( project, markerCount );
    }

}
