// Copyright (c) 2005-2006 by Leif Frenzel.
// All rights reserved.
package org.projectusus.core.internal.yellowcount;

import static org.eclipse.core.resources.IMarker.PROBLEM;
import static org.eclipse.core.resources.IResource.DEPTH_INFINITE;
import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.projectusus.core.internal.UsusCorePlugin;
import org.projectusus.core.internal.project.FindUsusProjects;

/**
 * <p>
 * the central singleton that counts occurences of yellow in the workspace and notifies also about changes.
 * </p>
 * 
 * @author Leif Frenzel
 */
public class YellowCount {

    private static YellowCount _instance;

    private final List<IYellowCountListener> listeners;

    private YellowCount() {
        listeners = new ArrayList<IYellowCountListener>();
        getWorkspace().addResourceChangeListener( new IResourceChangeListener() {
            public void resourceChanged( final IResourceChangeEvent event ) {
                notifyListeners();
            }
        }, IResourceChangeEvent.POST_BUILD | IResourceChangeEvent.POST_CHANGE );
    }

    public static synchronized YellowCount getInstance() {
        if( _instance == null ) {
            _instance = new YellowCount();
        }
        return _instance;
    }

    public void addYellowCountListener( final IYellowCountListener listener ) {
        listeners.add( listener );
    }

    public void removeYellowCountListener( final IYellowCountListener listener ) {
        listeners.remove( listener );
    }

    public IYellowCountResult count() {
        int yellowCount = 0;
        int yellowProjectCount = 0;
        List<IProject> projects = getUsusProjects();
        for( IProject project : projects ) {
            try {
                int projectYellowCount = getProjectYellowCount( project );
                yellowCount += projectYellowCount;
                if( projectYellowCount > 0 ) {
                    yellowProjectCount++;
                }
            } catch( CoreException cex ) {
                UsusCorePlugin.getDefault().getLog().log( cex.getStatus() );
            }
        }
        return createResult( yellowCount, yellowProjectCount, projects.size() );
    }

    private List<IProject> getUsusProjects() {
        IWorkspaceRoot wsRoot = getWorkspace().getRoot();
        return new FindUsusProjects( wsRoot.getProjects() ).compute();
    }

    private int getProjectYellowCount( IProject project ) throws CoreException {
        int result = 0;
        IMarker[] markers = project.findMarkers( PROBLEM, true, DEPTH_INFINITE );
        for( IMarker marker : markers ) {
            if( isWarning( marker ) ) {
                result++;
            }
        }
        return result;
    }

    private boolean isWarning( final IMarker marker ) throws CoreException {
        Integer severity = (Integer)marker.getAttribute( IMarker.SEVERITY );
        return severity != null && severity.intValue() == IMarker.SEVERITY_WARNING;
    }

    private void notifyListeners() {
        for( IYellowCountListener listener : listeners ) {
            listener.yellowCountChanged();
        }
    }

    private IYellowCountResult createResult( final int yellowCount, final int yellowProjectCount, final int projectCount ) {
        return new YellowCountResult( projectCount, yellowCount, yellowProjectCount );
    }
}
