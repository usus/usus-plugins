// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;



import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.junit.Assert.assertEquals;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.junit.After;
import org.junit.Test;
import org.projectusus.core.internal.PDETestUsingWSProject;
import org.projectusus.core.internal.proportions.modelcomputation.ICodeProportionComputationTarget;


public class FileChangeNotificationsPDETest extends PDETestUsingWSProject {

    private TestResourceChangeListener listener = new TestResourceChangeListener();
    
    @After
    public void tearDown() throws CoreException {
        getWorkspace().removeResourceChangeListener( listener );
        super.tearDown();
    }
    
    @Test
    public void fileAdded() throws Exception {
        getWorkspace().addResourceChangeListener( listener );
        IFile file = createWSFile( "Bla.java", "really interesting stuff" );
        
        listener.assertNoException();
        
        ICodeProportionComputationTarget target = listener.getTarget();
        assertEquals( 1, target.getProjects().size() );
        IProject affectedProject = target.getProjects().iterator().next();
        assertEquals( project, affectedProject );
        assertEquals( 1, target.getFiles( affectedProject ).size() );
        IFile affectedFile = target.getFiles( affectedProject ).iterator().next();
        assertEquals( file, affectedFile );
    }
    
    @Test
    public void fileDeleted() throws Exception {
        IFile file = createWSFile( "Bla.java", "stuff that didn't survive" );
        
        getWorkspace().addResourceChangeListener( listener );
        file.delete( true, new NullProgressMonitor() );
        waitForAutobuild();
        
        listener.assertNoException();
        
        ICodeProportionComputationTarget target = listener.getTarget();
        assertEquals( 1, target.getProjects().size() );
        IProject affectedProject = target.getProjects().iterator().next();
        assertEquals( project, affectedProject );
        assertEquals( 0, target.getFiles( affectedProject ).size() );
        assertEquals( 1, target.getRemovedFiles( affectedProject ).size() );
        IFile affectedFile = target.getRemovedFiles( affectedProject ).iterator().next();
        assertEquals( file, affectedFile );
    }
    
    @Test
    public void fileChanged() throws Exception {
        IFile file = createWSFile( "Bla.java", "stuff that will be replaced" );
        
        getWorkspace().addResourceChangeListener( listener );
        updateFileContent( file, "replacement" );
        
        listener.assertNoException();
        
        ICodeProportionComputationTarget target = listener.getTarget();
        assertEquals( 1, target.getProjects().size() );
        IProject affectedProject = target.getProjects().iterator().next();
        assertEquals( project, affectedProject );
        assertEquals( 0, target.getRemovedFiles( affectedProject ).size() );
        assertEquals( 1, target.getFiles( affectedProject ).size() );
        IFile affectedFile = target.getFiles( affectedProject ).iterator().next();
        assertEquals( file, affectedFile );
    }

    @Test
    public void fileMoved() throws Exception {
        IFile file = createWSFile( "Bla.java", "stuff that will be replaced" );
        IFolder folder = createWSFolder( "dir" );
        
        getWorkspace().addResourceChangeListener( listener );
        file.move( folder.getFullPath().append( file.getName() ), true, new NullProgressMonitor() );
        waitForAutobuild();
        
        listener.assertNoException();
        
        ICodeProportionComputationTarget target = listener.getTarget();
        IProject affectedProject = target.getProjects().iterator().next();
        
        // original file comes along as deleted
        assertEquals( 1, target.getRemovedFiles( affectedProject ).size() );
        IFile removedFile = target.getRemovedFiles( affectedProject ).iterator().next();
        assertEquals( new Path( "/p/Bla.java" ), removedFile.getFullPath() );
        
        // file at new location is registered as affected file
        assertEquals( 1, target.getFiles( affectedProject ).size() );
        IFile affectedFile = target.getFiles( affectedProject ).iterator().next();
        assertEquals( new Path( "/p/dir/Bla.java" ), affectedFile.getFullPath() );
    }

    @Test
    public void multipleFiles() throws Exception {
        getWorkspace().addResourceChangeListener( listener );
        // run in batch so that we get only one cumulative notification
        getWorkspace().run( new IWorkspaceRunnable() {
            public void run( IProgressMonitor monitor ) throws CoreException {
                createWSFilePlain( "a.java", "really" );
                createWSFilePlain( "b.java", "interesting" );
                createWSFilePlain( "c.java", "stuff" );
            }
        }, new NullProgressMonitor());
        waitForAutobuild();
        
        listener.assertNoException();
        
        ICodeProportionComputationTarget target = listener.getTarget();
        IProject affectedProject = target.getProjects().iterator().next();
        assertEquals( 3, target.getFiles( affectedProject ).size() );
    }
}
