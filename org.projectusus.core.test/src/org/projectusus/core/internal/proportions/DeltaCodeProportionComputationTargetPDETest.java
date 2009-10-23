// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;



import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.junit.Assert.assertEquals;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.junit.After;
import org.junit.Test;


public class DeltaCodeProportionComputationTargetPDETest extends TestUsingWSProject {

    // TODO lf test cases:
    //
    // file added, removed, changed content, moved
    // project created, opened, closed, deleted
    // project added and removed from usus projects


    private TestResourceChangeListener listener = new TestResourceChangeListener();
    
    @After
    public void tearDown() throws CoreException {
        getWorkspace().removeResourceChangeListener( listener );
        super.tearDown();
    }
    
    @Test
    public void fileAdded() throws Exception {
        getWorkspace().addResourceChangeListener( listener );
        IFile file = createWSFile( "bla", "really interesting stuff" );
        
        assertNoException( listener );
        ICodeProportionComputationTarget target = listener.getTarget();
        assertEquals( 1, target.getProjects().size() );
        IProject affectedProject = target.getProjects().iterator().next();
        assertEquals( project, affectedProject );
        assertEquals( 1, target.getFiles( affectedProject ).size() );
        IFile affectedFile = target.getFiles( affectedProject ).iterator().next();
        assertEquals( file, affectedFile );
    }
    
    private void assertNoException( TestResourceChangeListener li ) throws Exception {
        // otherwise it would be lost somewhere in the log, we want it to make the test red
        if( li.getException() != null ) {
            throw li.getException();
        }
    }

    private final class TestResourceChangeListener implements IResourceChangeListener {
        private DeltaCodeProportionComputationTarget target;
        private Exception exception;

        public void resourceChanged( IResourceChangeEvent event ) {
            IResourceDelta delta = event.getDelta();
            try {
                target = new DeltaCodeProportionComputationTarget( delta );
            } catch( CoreException cex ) {
                exception = cex;
            }
        }
        
        ICodeProportionComputationTarget getTarget() {
            return target;
        }
        
        Exception getException() {
            return exception;
        }
    }
}
