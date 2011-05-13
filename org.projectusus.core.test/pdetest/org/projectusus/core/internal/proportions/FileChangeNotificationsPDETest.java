// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.projectusus.adapter.ICodeProportionComputationTarget;
import org.projectusus.core.internal.PDETestUsingWSProject;

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
        IFile file = createWSFile( "Bla.java", "really interesting stuff", project1 );

        listener.assertNoException();

        ICodeProportionComputationTarget target = listener.getTarget();
        assertEquals( 1, target.getProjects().size() );
        assertThatAffectedProjectIsTheExpectedOne( target );
        assertEquals( 1, target.getFiles( project1 ).size() );
        IFile affectedFile = target.getFiles( project1 ).iterator().next();
        assertEquals( file, affectedFile );
    }

    @Test
    public void fileDeleted() throws Exception {
        IFile file = createWSFile( "Bla.java", "stuff that didn't survive", project1 );
        buildFullyAndWait();

        getWorkspace().addResourceChangeListener( listener );
        file.delete( true, new NullProgressMonitor() );

        listener.assertNoException();

        ICodeProportionComputationTarget target = listener.getTarget();
        assertEquals( 1, target.getProjects().size() );
        assertThatAffectedProjectIsTheExpectedOne( target );
        assertEquals( 0, target.getFiles( project1 ).size() );
        assertEquals( 1, target.getRemovedFiles( project1 ).size() );
        IFile affectedFile = target.getRemovedFiles( project1 ).iterator().next();
        assertEquals( file, affectedFile );
    }

    @Test
    public void fileChanged() throws Exception {
        IFile file = createWSFile( "Bla.java", "stuff that will be replaced", project1 );
        buildFullyAndWait();

        getWorkspace().addResourceChangeListener( listener );
        updateFileContent( file, "replacement" );

        listener.assertNoException();

        ICodeProportionComputationTarget target = listener.getTarget();
        assertEquals( 1, target.getProjects().size() );
        assertThatAffectedProjectIsTheExpectedOne( target );
        assertEquals( 0, target.getRemovedFiles( project1 ).size() );
        assertEquals( 1, target.getFiles( project1 ).size() );
        IFile affectedFile = target.getFiles( project1 ).iterator().next();
        assertEquals( file, affectedFile );
    }

    @Test
    public void fileMoved() throws Exception {
        IFile file = createWSFile( "Bla.java", "stuff that will be replaced", project1 );
        IFolder folder = createWSFolder( "dir", project1 );
        buildFullyAndWait();

        getWorkspace().addResourceChangeListener( listener );
        file.move( folder.getFullPath().append( file.getName() ), true, new NullProgressMonitor() );

        listener.assertNoException();

        ICodeProportionComputationTarget target = listener.getTarget();
        assertEquals( 1, target.getProjects().size() );
        assertThatAffectedProjectIsTheExpectedOne( target );
        // original file comes along as deleted
        assertEquals( 1, target.getRemovedFiles( project1 ).size() );
        IFile removedFile = target.getRemovedFiles( project1 ).iterator().next();
        assertEquals( new Path( "/" + PROJECT_NAME_1 + "/Bla.java" ), removedFile.getFullPath() );

        // file at new location is registered as affected file
        assertEquals( 1, target.getFiles( project1 ).size() );
        IFile affectedFile = target.getFiles( project1 ).iterator().next();
        assertEquals( new Path( "/" + PROJECT_NAME_1 + "/dir/Bla.java" ), affectedFile.getFullPath() );
    }

    @Test
    @Ignore( "temporarily broken?" )
    public void multipleFiles() throws Exception {
        getWorkspace().addResourceChangeListener( listener );
        // run in batch so that we get only one cumulative notification
        getWorkspace().run( new IWorkspaceRunnable() {
            public void run( IProgressMonitor monitor ) throws CoreException {
                createWSFile( "a.java", "really", project1 );
                createWSFile( "b.java", "interesting", project1 );
                createWSFile( "c.java", "stuff", project1 );
            }
        }, new NullProgressMonitor() );
        buildFullyAndWait();

        listener.assertNoException();

        ICodeProportionComputationTarget target = listener.getTarget();
        assertEquals( 2, target.getProjects().size() );
        assertThatAffectedProjectIsTheExpectedOne( target );
        IProject affectedProject = project1;
        assertEquals( 3, target.getFiles( affectedProject ).size() );
    }

    private void assertThatAffectedProjectIsTheExpectedOne( ICodeProportionComputationTarget target ) {
        assertTrue( target.getProjects().contains( project1 ) );
    }

}
