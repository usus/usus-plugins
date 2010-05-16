// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal;

import static org.eclipse.core.resources.IncrementalProjectBuilder.FULL_BUILD;
import static org.eclipse.core.resources.IncrementalProjectBuilder.INCREMENTAL_BUILD;
import static org.eclipse.core.resources.ResourcesPlugin.FAMILY_AUTO_BUILD;
import static org.eclipse.core.resources.ResourcesPlugin.FAMILY_AUTO_REFRESH;
import static org.eclipse.core.resources.ResourcesPlugin.FAMILY_MANUAL_BUILD;
import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.eclipse.core.runtime.jobs.Job.getJobManager;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.JavaCore;
import org.junit.After;
import org.junit.Before;
import org.projectusus.adapter.UsusAdapterPlugin;
import org.projectusus.core.internal.project.IUSUSProject;

public class PDETestUsingWSProject {

    protected static final String PROJECT_NAME = "p";
    protected IProject project;

    @Before
    public void setUp() throws CoreException {
        project = getWorkspace().getRoot().getProject( PROJECT_NAME );
        project.create( new NullProgressMonitor() );
        project.open( new NullProgressMonitor() );
        makeUsusProject( true );
        addJavaNature();
        UsusCorePlugin.getUsusModelForAdapter().dropRawData( project );
        UsusAdapterPlugin.getDefault(); // to load bundle with ResourceChangeListener
    }

    @After
    public void tearDown() throws CoreException {
        project.delete( true, new NullProgressMonitor() );
    }

    protected void buildFullyAndWait() throws CoreException {
        getWorkspace().build( FULL_BUILD, new NullProgressMonitor() );
        System.out.print( "  Waiting for full build to complete ..." );
        waitForBuild();
    }

    protected void buildIncrementallyAndWait() throws CoreException {
        getWorkspace().build( INCREMENTAL_BUILD, new NullProgressMonitor() );
        System.out.print( "  Waiting for incremental build to complete ..." );
        waitForBuild();
    }

    private void waitForBuild() {
        boolean retry = true;
        while( retry ) {
            try {
                getJobManager().join( FAMILY_AUTO_REFRESH, new NullProgressMonitor() );
                getJobManager().join( FAMILY_AUTO_BUILD, new NullProgressMonitor() );
                getJobManager().join( FAMILY_MANUAL_BUILD, new NullProgressMonitor() );
                retry = false;
            } catch( Exception exc ) {
                // ignore and retry
            }
        }
        System.out.println( " OK." );
    }

    protected IFile createWSFile( String fileName, String content ) throws CoreException {
        IFile result = project.getFile( fileName );
        result.create( createInputStream( content ), true, new NullProgressMonitor() );
        return result;
    }

    protected void deleteWSFile( IFile file ) throws CoreException {
        file.delete( true, new NullProgressMonitor() );
    }

    protected void updateFileContent( IFile file, String newContent ) throws CoreException {
        file.setContents( createInputStream( newContent ), true, false, new NullProgressMonitor() );
    }

    protected IFolder createWSFolder( String name ) throws CoreException {
        IFolder result = project.getFolder( name );
        result.create( true, true, new NullProgressMonitor() );
        return result;
    }

    protected void makeUsusProject( boolean makeUsusProject ) throws CoreException {
        getUsusProjectAdapter().setUsusProject( makeUsusProject );
    }

    private IUSUSProject getUsusProjectAdapter() {
        return (IUSUSProject)project.getAdapter( IUSUSProject.class );
    }

    private void addJavaNature() throws CoreException {
        IProjectDescription description = project.getDescription();
        description.setNatureIds( new String[] { JavaCore.NATURE_ID } );
        project.setDescription( description, new NullProgressMonitor() );
    }

    private InputStream createInputStream( String content ) {
        return new ByteArrayInputStream( content.getBytes() );
    }
}
