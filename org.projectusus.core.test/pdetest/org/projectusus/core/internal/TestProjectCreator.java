// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal;

import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.JavaCore;
import org.projectusus.core.internal.project.IUSUSProject;
import org.projectusus.core.internal.proportions.rawdata.UsusModel;

public class TestProjectCreator {

    private IProject project;

    TestProjectCreator( String name ) throws CoreException {
        super();
        newProjectNamed( name );
    }

    IProject getProject() {
        return project;
    }

    private void newProjectNamed( String name ) throws CoreException {
        project = getWorkspace().getRoot().getProject( name );
        project.create( new NullProgressMonitor() );
        project.open( new NullProgressMonitor() );
        makeUsusProject( true, project );
        addJavaNature();
        UsusModel.ususModel().dropRawData( project );
    }

    private void addJavaNature() throws CoreException {
        IProjectDescription description = project.getDescription();
        description.setNatureIds( new String[] { JavaCore.NATURE_ID } );
        project.setDescription( description, new NullProgressMonitor() );
    }

    static void makeUsusProject( boolean makeUsusProject, IProject theProject ) throws CoreException {
        ((IUSUSProject)theProject.getAdapter( IUSUSProject.class )).setUsusProject( makeUsusProject );
    }

}
