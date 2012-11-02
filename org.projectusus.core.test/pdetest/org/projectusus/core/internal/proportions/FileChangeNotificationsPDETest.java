// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;
import static org.junit.matchers.JUnitMatchers.hasItems;
import static org.projectusus.core.internal.TestProjectCreator.SOURCE_FOLDER;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.projectusus.adapter.ICodeProportionComputationTarget;
import org.projectusus.core.internal.JavaProject;
import org.projectusus.core.internal.Workspace;

public class FileChangeNotificationsPDETest {

    @Rule
    public Workspace workspace = new Workspace();

    @Rule
    public JavaProject project1 = new JavaProject();

    @Rule
    public JavaProject project2 = new JavaProject();

    private TestResourceChangeListener listener = new TestResourceChangeListener();

    @After
    public void tearDown() throws CoreException {
        getWorkspace().removeResourceChangeListener( listener );
    }

    @Test
    public void fileAdded() throws Exception {
        getWorkspace().addResourceChangeListener( listener );
        IFile file = project1.createFile( "Bla.java", "really interesting stuff" );

        listener.assertNoException();

        ICodeProportionComputationTarget target = listener.getTarget();
        assertEquals( 1, target.getProjects().size() );
        assertThat( target.getProjects(), hasItem( project1.get() ) );
        assertEquals( 1, target.getFiles( project1.get() ).size() );
        IFile affectedFile = target.getFiles( project1.get() ).iterator().next();
        assertEquals( file, affectedFile );
    }

    @Test
    public void fileDeleted() throws Exception {
        IFile file = project1.createFile( "Bla.java", "stuff that didn't survive" );
        workspace.buildFullyAndWait();

        getWorkspace().addResourceChangeListener( listener );
        file.delete( true, new NullProgressMonitor() );

        listener.assertNoException();

        ICodeProportionComputationTarget target = listener.getTarget();
        assertEquals( 1, target.getProjects().size() );
        assertThat( target.getProjects(), hasItem( project1.get() ) );
        assertEquals( 0, target.getFiles( project1.get() ).size() );
        assertEquals( 1, target.getRemovedFiles( project1.get() ).size() );
        IFile affectedFile = target.getRemovedFiles( project1.get() ).iterator().next();
        assertEquals( file, affectedFile );
    }

    @Test
    public void fileChanged() throws Exception {
        IFile file = project1.createFile( "Bla.java", "stuff that will be replaced" );
        workspace.buildFullyAndWait();

        getWorkspace().addResourceChangeListener( listener );
        project1.updateContent( file, "replacement" );

        listener.assertNoException();

        ICodeProportionComputationTarget target = listener.getTarget();
        assertEquals( 1, target.getProjects().size() );
        assertThat( target.getProjects(), hasItem( project1.get() ) );
        assertEquals( 0, target.getRemovedFiles( project1.get() ).size() );
        assertEquals( 1, target.getFiles( project1.get() ).size() );
        IFile affectedFile = target.getFiles( project1.get() ).iterator().next();
        assertEquals( file, affectedFile );
    }

    @Test
    public void fileMoved() throws Exception {
        IFile file = project1.createFile( "Bla.java", "stuff that will be replaced" );
        IFolder folder = project1.createFolder( "dir" );
        workspace.buildFullyAndWait();

        getWorkspace().addResourceChangeListener( listener );
        file.move( folder.getFullPath().append( file.getName() ), true, new NullProgressMonitor() );

        listener.assertNoException();

        ICodeProportionComputationTarget target = listener.getTarget();
        assertEquals( 1, target.getProjects().size() );
        assertThat( target.getProjects(), hasItem( project1.get() ) );
        // original file comes along as deleted
        assertEquals( 1, target.getRemovedFiles( project1.get() ).size() );
        IFile removedFile = target.getRemovedFiles( project1.get() ).iterator().next();
        assertEquals( new Path( "/" + project1.get().getName() + "/" + SOURCE_FOLDER + "/Bla.java" ), removedFile.getFullPath() );

        // file at new location is registered as affected file
        assertEquals( 1, target.getFiles( project1.get() ).size() );
        IFile affectedFile = target.getFiles( project1.get() ).iterator().next();
        assertEquals( new Path( "/" + project1.get().getName() + "/" + SOURCE_FOLDER + "/dir/Bla.java" ), affectedFile.getFullPath() );
    }

    @Test
    public void multipleFilesCreatedAtOnce() throws Exception {
        getWorkspace().addResourceChangeListener( listener );
        // run in batch so that we get only one cumulative notification
        getWorkspace().run( new IWorkspaceRunnable() {
            public void run( IProgressMonitor monitor ) throws CoreException {
                project1.createFile( "a.java", "really" );
                project1.createFile( "b.java", "interesting" );
                project2.createFile( "c.java", "stuff" );
            }
        }, new NullProgressMonitor() );
        workspace.buildFullyAndWait();

        listener.assertNoException();
        ICodeProportionComputationTarget target = listener.getTarget();

        Collection<IProject> affectedProjects = target.getProjects();
        assertEquals( 2, affectedProjects.size() );
        assertThat( affectedProjects, hasItems( project1.get(), project2.get() ) );

        assertEquals( 2, target.getFiles( project1.get() ).size() );
        assertEquals( 1, target.getFiles( project2.get() ).size() );
    }

}
