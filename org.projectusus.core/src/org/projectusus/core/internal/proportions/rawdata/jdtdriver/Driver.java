package org.projectusus.core.internal.proportions.rawdata.jdtdriver;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;

public abstract class Driver {

    protected int getNumberOfWarnings( IMarker[] markers ) throws CoreException {
        int markerCount = 0;
        for( IMarker marker : markers ) {
            if( isWarning( marker ) ) {
                markerCount++;
            }
        }
        return markerCount;
    }

    private boolean isWarning( final IMarker marker ) throws CoreException {
        Integer severity = (Integer)marker.getAttribute( IMarker.SEVERITY );
        return severity != null && severity.intValue() == IMarker.SEVERITY_WARNING;
    }

}
