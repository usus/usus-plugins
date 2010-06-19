package org.projectusus.core.basis;

import static org.eclipse.core.resources.IMarker.PROBLEM;
import static org.eclipse.core.resources.IResource.DEPTH_INFINITE;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

public class YellowCountCache {

    private static YellowCountCache instance = new YellowCountCache();

    private Map<IProject, Integer> projectCounts = new HashMap<IProject, Integer>();

    public static YellowCountCache yellowCountCache() {
        return instance;
    }

    private YellowCountCache() {
        super();
    }

    public void clear() {
        projectCounts = new HashMap<IProject, Integer>();
    }

    public void add( IProject project ) {
        try {
            projectCounts.put( project, getNumberOfWarnings( project.findMarkers( PROBLEM, true, DEPTH_INFINITE ) ) );
        } catch( CoreException e ) {
            // do nothing
        }
    }

    public void clear( IProject project ) {
        projectCounts.remove( project );
    }

    public YellowCountResult getResult() {
        return new YellowCountResult( projects(), yellows(), yellowProjects() );
    }

    public int yellowProjects() {
        int yellowProjects = 0;
        for( Integer count : projectCounts.values() ) {
            if( count.intValue() > 0 ) {
                yellowProjects++;
            }
        }
        return yellowProjects;
    }

    public int yellows() {
        int yellows = 0;
        for( Integer count : projectCounts.values() ) {
            yellows += count.intValue();
        }
        return yellows;
    }

    public int projects() {
        return projectCounts.size();
    }

    private Integer getNumberOfWarnings( IMarker[] markers ) throws CoreException {
        int markerCount = 0;
        for( IMarker marker : markers ) {
            if( isWarning( marker ) ) {
                markerCount++;
            }
        }
        return new Integer( markerCount );
    }

    private boolean isWarning( final IMarker marker ) throws CoreException {
        Integer severity = (Integer)marker.getAttribute( IMarker.SEVERITY );
        return severity != null && severity.intValue() == IMarker.SEVERITY_WARNING;
    }

}
