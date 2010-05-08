// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

import static org.eclipse.core.runtime.jobs.Job.getJobManager;
import static org.junit.Assert.assertEquals;
import static org.projectusus.core.internal.UsusCorePlugin.getUsusModel;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.IUsusModelListener;
import org.projectusus.core.internal.UsusCorePlugin;
import org.projectusus.core.internal.proportions.modelcomputation.CodeProportionsComputerJob;

public class CodeProportionsPDETest {

    @Before
    public void setUp() throws Exception {
        getJobManager().join( CodeProportionsComputerJob.FAMILY, new NullProgressMonitor() );
    }
    
    @Test
    public void listenerHandling() throws InterruptedException {
        IUsusModelWriteAccess ususModelWriteAccess = UsusCorePlugin.getUsusModelWriteAccess();
        DummyCodeProportionsListener listener = new DummyCodeProportionsListener();
        getUsusModel().addUsusModelListener( listener );

        ususModelWriteAccess.updateAfterComputationRun( false, new NullProgressMonitor() );
        assertEquals( 1, listener.getCallCount() );

        getUsusModel().removeUsusModelListener( listener );
        ususModelWriteAccess.updateAfterComputationRun( false, new NullProgressMonitor() );
        // no more calls
        assertEquals( 1, listener.getCallCount() );
    }

    private final class DummyCodeProportionsListener implements IUsusModelListener {
        private int callCount;

        public void ususModelChanged(  ) {
          callCount++;
        }

        public int getCallCount() {
            return callCount;
        }
    }
}
