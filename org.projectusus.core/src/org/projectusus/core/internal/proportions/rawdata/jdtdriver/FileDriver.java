package org.projectusus.core.internal.proportions.rawdata.jdtdriver;

import static org.eclipse.core.resources.IMarker.PROBLEM;
import static org.eclipse.core.resources.IResource.DEPTH_INFINITE;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.projectusus.core.internal.UsusCorePlugin;

public class FileDriver extends Driver {

    protected final IFile file;

    public FileDriver( IFile file ) {
        this.file = file;
    }

    public void compute() {
        try {
            countYellowMarkers();
        } catch( CoreException cex ) {
            UsusCorePlugin.log( cex );
        }
    }

    private void countYellowMarkers() throws CoreException {
        IMarker[] markers = file.findMarkers( PROBLEM, true, DEPTH_INFINITE );
        int markerCount = getNumberOfWarnings( markers );
        UsusCorePlugin.getUsusModelMetricsWriter().setWarningsCount( file, markerCount );
    }
}
