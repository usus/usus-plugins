// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal;

import static org.eclipse.core.resources.IncrementalProjectBuilder.CLEAN_BUILD;
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
import org.projectusus.core.internal.project.IUSUSProject;

public class PDETestUsingWSProject {

    protected IProject project;
    
    @Before
    public void setUp() throws CoreException {
        project = getWorkspace().getRoot().getProject( "p" );
        project.create( new NullProgressMonitor() );
        project.open( new NullProgressMonitor() );
        makeUsusProject( true );
        waitForAutobuild();
    }

    @After
    public void tearDown() throws CoreException { 
        project.delete( true, new NullProgressMonitor() );
        waitForAutobuild();
    }

    protected void waitForAutobuild() throws CoreException {
        getWorkspace().build( CLEAN_BUILD, new NullProgressMonitor() );
        System.out.print( "  Waiting for autobuild to complete ..." );
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
        System.out.print( " OK.\n" );
    }

    protected IFile createWSFilePlain( String fileName, String content ) throws CoreException {
        IFile result = project.getFile( fileName );
        result.create( createInputStream( content ), true, new NullProgressMonitor() );
        return result;
    }
    
    protected IFile createWSFile( String fileName, String content ) throws CoreException {
        IFile result = createWSFilePlain( fileName, content );
        waitForAutobuild();
        return result;
    }

    protected void updateFileContent( IFile file, String newContent ) throws CoreException {
        file.setContents( createInputStream( newContent ), true, false, new NullProgressMonitor() );
        waitForAutobuild();
    }
    
    protected IFolder createWSFolder( String name ) throws CoreException {
        IFolder result = project.getFolder( name );
        result.create( true, true, new NullProgressMonitor() );
        waitForAutobuild();
        return result;
    }
    
    protected void makeUsusProject( boolean makeUsusProject ) throws CoreException {
        Object adapter = project.getAdapter( IUSUSProject.class );
        ((IUSUSProject)adapter).setUsusProject( makeUsusProject );
        waitForAutobuild();
    }
    
    protected void addJavaNature() throws CoreException {
        IProjectDescription description = project.getDescription();
        description.setNatureIds( new String[] { JavaCore.NATURE_ID } );
        project.setDescription( description, new NullProgressMonitor() );
        waitForAutobuild();
    }
    
    private InputStream createInputStream( String content ) {
        return new ByteArrayInputStream( content.getBytes() );
    }
}
