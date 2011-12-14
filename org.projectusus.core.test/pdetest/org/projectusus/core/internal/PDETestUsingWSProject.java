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
import static org.junit.Assert.fail;
import static org.projectusus.core.internal.TestProjectCreator.SOURCE_FOLDER;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.After;
import org.junit.Before;
import org.projectusus.adapter.UsusAdapterPlugin;
import org.projectusus.core.internal.proportions.rawdata.UsusModel;

public class PDETestUsingWSProject {

    protected static final String PROJECT_NAME_1 = "p1";
    protected static final String PROJECT_NAME_2 = "p2";
    protected IProject project1;
    protected IProject project2;

    @Before
    public void createProjects() {
        project1 = createProject( PROJECT_NAME_1 );
        project2 = createProject( PROJECT_NAME_2 );
        UsusAdapterPlugin.getDefault(); // to load bundle with ResourceChangeListener
    }

    private IProject createProject( String projectName ) {
        System.out.print( "  Creating project '" + projectName + "' at " + System.nanoTime() + " ..." );
        System.out.flush();
        IProject project = null;
        CoreException exception = null;
        try {
            project = new TestProjectCreator( projectName ).getProject();
        } catch( CoreException e ) {
            exception = e;
        }
        logResult( projectName, project, exception );
        return project;
    }

    private void logResult( String projectName, IProject project, CoreException exception ) {
        if( project != null && project.exists() ) {
            System.out.println( " OK." );
        } else {
            System.out.println( " FAILED." );
            String message = "Could not create project '" + projectName + "'";
            if( exception != null ) {
                exception.printStackTrace( System.out );
                message += ": " + exception.getMessage();
            }
            fail( message );
        }
    }

    @After
    public void tearDown() throws CoreException {
        delete( project1 );
        delete( project2 );
        UsusModel.clear();
    }

    private void delete( IProject project ) throws CoreException {
        System.out.print( "  Deleting project '" + project.getName() + "' at " + System.nanoTime() + " ..." );
        System.out.flush();
        project.delete( true, null );
        if( project.exists() ) {
            System.out.println( " FAILED." );
            fail( "Could not delte project '" + project.getName() + "'" );
        } else {
            System.out.println( " OK." );
        }
    }

    protected void buildFullyAndWait() throws CoreException {
        getWorkspace().build( FULL_BUILD, new NullProgressMonitor() );
        System.out.print( "  Waiting for full build to complete ..." );
        System.out.flush();
        waitForBuild();
    }

    protected void buildIncrementallyAndWait() throws CoreException {
        getWorkspace().build( INCREMENTAL_BUILD, new NullProgressMonitor() );
        System.out.print( "  Waiting for incremental build to complete ..." );
        System.out.flush();
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

    protected IFile createWSFile( String fileName, String content, IProject project ) throws CoreException {
        IFile result = project.getFile( SOURCE_FOLDER + "/" + fileName );
        result.create( createInputStream( content ), true, new NullProgressMonitor() );
        return result;
    }

    protected void deleteWSFile( IFile file ) throws CoreException {
        file.delete( true, new NullProgressMonitor() );
    }

    protected void updateFileContent( IFile file, String newContent ) throws CoreException {
        file.setContents( createInputStream( newContent ), true, false, new NullProgressMonitor() );
    }

    protected IFolder createWSFolder( String name, IProject project ) throws CoreException {
        IFolder result = project.getFolder( SOURCE_FOLDER + "/" + name );
        result.create( true, true, new NullProgressMonitor() );
        return result;
    }

    protected void makeUsusProject( boolean makeUsusProject, IProject project ) throws CoreException {
        TestProjectCreator.makeUsusProject( makeUsusProject, project );
    }

    private InputStream createInputStream( String content ) {
        return new ByteArrayInputStream( content.getBytes() );
    }
}
