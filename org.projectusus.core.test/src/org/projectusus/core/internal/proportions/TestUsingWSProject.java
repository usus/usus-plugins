// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

import static org.eclipse.core.resources.IncrementalProjectBuilder.CLEAN_BUILD;
import static org.eclipse.core.resources.ResourcesPlugin.FAMILY_AUTO_BUILD;
import static org.eclipse.core.resources.ResourcesPlugin.FAMILY_AUTO_REFRESH;
import static org.eclipse.core.resources.ResourcesPlugin.FAMILY_MANUAL_BUILD;
import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.eclipse.core.runtime.jobs.Job.getJobManager;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.After;
import org.junit.Before;

public class TestUsingWSProject {

    protected IProject project;
    
    @Before
    public void setUp() throws CoreException {
        project = getWorkspace().getRoot().getProject( "p" );
        project.create( new NullProgressMonitor() );
        project.open( new NullProgressMonitor() );
        waitForAutobuild();
    }

    @After
    public void tearDown() throws CoreException { 
        project.delete( true, new NullProgressMonitor() );
        waitForAutobuild();
    }

    protected void waitForAutobuild() throws CoreException {
        getWorkspace().build( CLEAN_BUILD, new NullProgressMonitor() );
        System.out.print( "  Waiting for autobuild to complete ..." ); //$NON-NLS-1$
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
        System.out.print( " OK.\n" ); //$NON-NLS-1$
    }

    protected IFile createWSFile( String fileName, String content ) throws CoreException {
        IFile file = project.getFile( fileName );
        file.create( createInputStream( content ), true, new NullProgressMonitor() );
        waitForAutobuild();
        return file;
    }

    private InputStream createInputStream( String content ) {
        return new ByteArrayInputStream( content.getBytes() );
    }
}
