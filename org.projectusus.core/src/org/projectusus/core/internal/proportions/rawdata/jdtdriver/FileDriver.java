package org.projectusus.core.internal.proportions.rawdata.jdtdriver;

import static org.eclipse.core.resources.IMarker.PROBLEM;
import static org.eclipse.core.resources.IResource.DEPTH_INFINITE;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.projectusus.core.internal.UsusCorePlugin;

public class FileDriver {

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
        int markerCount = 0;
        for( IMarker marker : markers ) {
            if( isWarning( marker ) ) {
                markerCount++;
            }
            UsusCorePlugin.getUsusModelMetricsWriter().setYellowCount( file, markerCount );
        }
    }

    private boolean isWarning( final IMarker marker ) throws CoreException {
        Integer severity = (Integer)marker.getAttribute( IMarker.SEVERITY );
        return severity != null && severity.intValue() == IMarker.SEVERITY_WARNING;
    }

}
