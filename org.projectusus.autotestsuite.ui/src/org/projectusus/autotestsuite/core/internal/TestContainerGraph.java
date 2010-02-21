// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.autotestsuite.core.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

public class TestContainerGraph implements ITestContainerGraph {

    private final IProject[] projects;

    TestContainerGraph( IProject[] projects ) {
        this.projects = projects;
    }

    public List<ITestContainer> getRootContainers() {
        List<ITestContainer> result = new ArrayList<ITestContainer>();
        for( IJavaProject javaProject : getRootProjects() ) {
            result.add( new TestContainer( javaProject ) );
        }
        return result;
    }

    public List<IJavaProject> getRootProjects() {
        List<IJavaProject> result = new ArrayList<IJavaProject>();
        for( IProject project : projects ) {
            loadJavaProject( project, result );
        }
        return result;
    }

    private void loadJavaProject( IProject project, List<IJavaProject> collector ) {
        IJavaProject javaProject = JavaCore.create( project );
        if( javaProject != null ) {
            collector.add( javaProject );
        }
    }
}
