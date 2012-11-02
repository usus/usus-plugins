// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.project;

import static java.util.Arrays.asList;
import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.Rule;
import org.junit.Test;
import org.projectusus.core.internal.JavaProject;
import org.projectusus.core.internal.Workspace;
import org.projectusus.core.project.FindUsusProjects;
import org.projectusus.core.project2.IUSUSProject;

public class FindUsusProjectsPDETest {

    @Rule
    public Workspace workspace = new Workspace();

    @Rule
    public JavaProject project1 = new JavaProject();

    @Rule
    public JavaProject project2 = new JavaProject();

    @Test
    public void empty() {
        List<IProject> result = new FindUsusProjects( new IProject[0] ).compute();
        assertTrue( result.isEmpty() );
    }

    @Test
    public void singleUsusProject() {
        assertEquals( 2, computeWithAllProjects().size() );
    }

    @Test
    public void singleNonUsusProject() throws CoreException {
        project1.disableUsus();
        workspace.buildFullyAndWait();
        assertEquals( 1, computeWithAllProjects().size() );

        project2.disableUsus();
        workspace.buildFullyAndWait();
        assertEquals( 0, computeWithAllProjects().size() );
    }

    @Test
    public void noProjects() throws CoreException {
        project1.delete();
        workspace.buildFullyAndWait();
        assertEquals( 1, computeWithAllProjects().size() );

        project2.delete();
        workspace.buildFullyAndWait();
        assertEquals( 0, computeWithAllProjects().size() );
    }

    @Test
    public void multipleProjectsMixed() throws CoreException {
        IProject bla = createAdditionalProject( "bla", true ); //$NON-NLS-1$
        IProject blubb = createAdditionalProject( "blubb", false ); //$NON-NLS-1$
        List<IProject> result = computeWithAllProjects();

        assertEquals( 3, result.size() );
        assertTrue( result.contains( project1.get() ) );
        assertTrue( result.contains( bla ) );

        bla.delete( true, new NullProgressMonitor() );
        blubb.delete( true, new NullProgressMonitor() );
    }

    @Test
    public void mixedCandidates() {
        Object[] candidates = new Object[] { new Object(), project1.get() };
        List<IProject> result = new FindUsusProjects( candidates ).compute();

        assertEquals( 1, result.size() );
        assertEquals( project1.get(), result.get( 0 ) );
    }

    private List<IProject> computeWithAllProjects() {
        return new FindUsusProjects( getAllProjectsFromWS() ).compute();
    }

    private List<IProject> getAllProjectsFromWS() {
        return asList( getWorkspace().getRoot().getProjects() );
    }

    private IProject createAdditionalProject( String name, boolean makeUsusProject ) throws CoreException {
        IProject result = getWorkspace().getRoot().getProject( name );
        result.create( new NullProgressMonitor() );
        result.open( new NullProgressMonitor() );
        IUSUSProject ususProject = (IUSUSProject)result.getAdapter( IUSUSProject.class );
        ususProject.setUsusProject( makeUsusProject );
        workspace.buildFullyAndWait();
        return result;
    }
}
