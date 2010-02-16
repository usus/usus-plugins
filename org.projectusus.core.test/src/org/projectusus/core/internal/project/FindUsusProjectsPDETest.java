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
import org.junit.Test;
import org.projectusus.core.internal.PDETestUsingWSProject;

public class FindUsusProjectsPDETest extends PDETestUsingWSProject {

    @Test
    public void empty() throws CoreException {
        List<IProject> result = new FindUsusProjects( new IProject[0] ).compute();
        assertTrue( result.isEmpty() );
    }

    @Test
    public void singleUsusProject() throws CoreException {
        assertEquals( 1, computeWithAllProjects().size() );
    }

    @Test
    public void singleNonUsusProject() throws CoreException {
        makeUsusProject( false );
        assertTrue( computeWithAllProjects().isEmpty() );
    }

    @Test
    public void noProjects() throws CoreException {
        project.delete( true, new NullProgressMonitor() );
        waitForAutobuild();
        assertTrue( computeWithAllProjects().isEmpty() );
    }

    @Test
    public void multipleProjectsMixed() throws CoreException {
        IProject bla = createAdditionalProject( "bla", true );
        createAdditionalProject( "blubb", false );
        List<IProject> result = computeWithAllProjects();

        assertEquals( 2, result.size() );
        assertTrue( result.contains( project ) );
        assertTrue( result.contains( bla ) );
    }

    @Test
    public void mixedCandidates() throws CoreException {
        Object[] candidates = new Object[] { new Object(), project };
        List<IProject> result = new FindUsusProjects( candidates ).compute();

        assertEquals( 1, result.size() );
        assertEquals( project, result.get( 0 ) );
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
        waitForAutobuild();
        return result;
    }
}
