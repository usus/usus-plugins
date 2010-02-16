// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.yellowcount;

import static java.util.Collections.sort;
import static java.util.Collections.unmodifiableList;
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
import org.projectusus.core.internal.proportions.model.ByHotnessComparator;
import org.projectusus.core.internal.proportions.model.IHotspot;

public class ProjectYellowCount {

    private final HashMap<IResource, Integer> counts;
    private final int yellowCount;
    private final List<IHotspot> hotspots;

    public ProjectYellowCount( IProject project ) throws CoreException {
        counts = new HashMap<IResource, Integer>();
        yellowCount = countYellowMarkers( project );
        hotspots = createHotspots();
    }

    public int getYellowCount() {
        return yellowCount;
    }

    public List<IHotspot> getHotspots() {
        return unmodifiableList( hotspots );
    }

    // internal
    // ////////

    private int countYellowMarkers( IProject project ) throws CoreException {
        IMarker[] markers = project.findMarkers( PROBLEM, true, DEPTH_INFINITE );
        for( IMarker marker : markers ) {
            if( isWarning( marker ) ) {
                increase( marker.getResource() );
            }
        }
        return sum();
    }

    private int sum() {
        int result = 0;
        for( IResource resource : counts.keySet() ) {
            result += counts.get( resource ).intValue();
        }
        return result;
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

    private List<IHotspot> createHotspots() {
        List<IHotspot> result = new ArrayList<IHotspot>();
        for( IResource resource : counts.keySet() ) {
            if( resource instanceof IFile ) {
                IFile file = (IFile)resource;
                int count = counts.get( resource ).intValue();
                result.add( new MetricCWHotspot( file, count ) );
            }
        }
        sort( result, new ByHotnessComparator() );
        return result;
    }
}
