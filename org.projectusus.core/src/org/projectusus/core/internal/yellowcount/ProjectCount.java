// Copyright (c) 2005-2006, 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.yellowcount;

import static org.eclipse.core.resources.IMarker.PROBLEM;
import static org.eclipse.core.resources.IResource.DEPTH_INFINITE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.projectusus.core.internal.proportions.model.IHotspot;

class ProjectCount {
    private final IProject project;
    private final HashMap<IResource, Integer> counts;

    ProjectCount( IProject project ) throws CoreException {
        this.project = project;
        counts = new HashMap<IResource, Integer>();
        computeProjectYellowCount();
    }

    public boolean isEmpty() {
        return counts.isEmpty();
    }

    int sum() {
        int result = 0;
        for( IResource resource : counts.keySet() ) {
            result += counts.get( resource ).intValue();
        }
        return result;
    }

    int countFiles() {
        return counts.size();
    }

    List<IHotspot> getHotspots() {
        List<IHotspot> result = new ArrayList<IHotspot>();
        for( IResource resource : counts.keySet() ) {
            IFile file = (IFile)resource;
            int count = counts.get( resource ).intValue();
            result.add( new MetricCWHotspot( file, count ) );
        }
        return result;
    }

    private void computeProjectYellowCount() throws CoreException {
        IMarker[] markers = project.findMarkers( PROBLEM, true, DEPTH_INFINITE );
        for( IMarker marker : markers ) {
            if( isWarning( marker ) ) {
                increase( marker.getResource() );
            }
        }
    }

    private void increase( IResource resource ) {
        Integer count = counts.get( resource );
        if( count == null ) {
            count = new Integer( 0 );
        }
        count = new Integer( count.intValue() + 1 );
        counts.put( resource, count );
    }

    private boolean isWarning( final IMarker marker ) throws CoreException {
        Integer severity = (Integer)marker.getAttribute( IMarker.SEVERITY );
        return severity != null && severity.intValue() == IMarker.SEVERITY_WARNING;
    }
}
