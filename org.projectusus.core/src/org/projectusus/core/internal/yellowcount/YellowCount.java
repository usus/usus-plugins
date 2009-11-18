// Copyright (c) 2005-2006, 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.yellowcount;

import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.projectusus.core.internal.project.FindUsusProjects;
import org.projectusus.core.internal.proportions.model.CodeProportion;
import org.projectusus.core.internal.proportions.yellowcount.WorkspaceYellowCount;

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
        List<IProject> projects = getUsusProjects();
        return convert( new WorkspaceYellowCount( getUsusProjects() ), projects );
    }

    private IYellowCountResult convert( WorkspaceYellowCount count, List<IProject> projects ) {
        return createResult( count.getCodeProportion(), projects.size(), count.countProjectsWithViolations() );
    }

    private List<IProject> getUsusProjects() {
        IWorkspaceRoot wsRoot = getWorkspace().getRoot();
        return new FindUsusProjects( wsRoot.getProjects() ).compute();
    }

    private void notifyListeners() {
        for( IYellowCountListener listener : listeners ) {
            listener.yellowCountChanged();
        }
    }

    private YellowCountResult createResult( CodeProportion codeProportion, int projectCount, int yellowProjectCount ) {
        return new YellowCountResult( projectCount, codeProportion.getBasis(), codeProportion.getViolations(), yellowProjectCount );
    }
}
