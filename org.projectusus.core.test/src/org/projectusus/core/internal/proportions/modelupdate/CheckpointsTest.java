// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.modelupdate;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;
import org.projectusus.core.internal.proportions.CodeProportion;
import org.projectusus.core.internal.proportions.modelupdate.Checkpoints;
import org.projectusus.core.internal.proportions.modelupdate.IUsusModelStatus;

public class CheckpointsTest {

    @Test
    public void noCreateWhenUnsuccessful() {
        Checkpoints checkpoints = new Checkpoints();
        callCreate( checkpoints, false, now(), now() );

        assertEquals( 0, checkpoints.getCheckpoints().size() );
    }

    @Test
    public void noCreateWhenNoTestRunDate() {
        Checkpoints checkpoints = new Checkpoints();
        callCreate( checkpoints, true, null, null );

        assertEquals( 0, checkpoints.getCheckpoints().size() );
    }

    @Test
    public void noCreateWhenNoIntermediateComputation() {
        Checkpoints checkpoints = new Checkpoints();
        Date computationRun = now();
        callCreate( checkpoints, true, null, computationRun );

        Date testRun = now();
        callCreate( checkpoints, true, testRun, computationRun );
        // now we have both computation and test -> first checkpoint
        assertEquals( 1, checkpoints.getCheckpoints().size() );

        Date secondTestRun = new Date( testRun.getTime() + 1000 );
        callCreate( checkpoints, true, secondTestRun, computationRun );

        // no new checkpoint created if no computations, just another test run
        assertEquals( 1, checkpoints.getCheckpoints().size() );
    }

    @Test
    public void noCreateWhenBetweenComputations() {
        Checkpoints checkpoints = new Checkpoints();
        Date computationRun = now();
        callCreate( checkpoints, true, null, computationRun );

        Date testRun = now();
        callCreate( checkpoints, true, testRun, computationRun );
        // now we have both computation and test -> first checkpoint
        assertEquals( 1, checkpoints.getCheckpoints().size() );

        Date secondComputationRun = new Date( computationRun.getTime() + 1000 );
        callCreate( checkpoints, true, testRun, secondComputationRun );
        // no new checkpoint created if no test run, just computations
        assertEquals( 1, checkpoints.getCheckpoints().size() );
    }

    @Test
    public void create() {
        Checkpoints checkpoints = new Checkpoints();
        callCreate( checkpoints, true, now(), null );

        assertEquals( 1, checkpoints.getCheckpoints().size() );
        // but no entry
        assertEquals( 0, checkpoints.getCheckpoints().get( 0 ).getEntries().size() );
    }

    private void callCreate( Checkpoints checkpoints, boolean success, Date lastTestRun, Date lastComputationRun ) {
        ArrayList<CodeProportion> entries = new ArrayList<CodeProportion>();
        IUsusModelStatus status = new DummyStatus( success, lastTestRun, lastComputationRun );
        checkpoints.createCheckpoint( status, entries );
    }

    private Date now() {
        return new Date();
    }

    private class DummyStatus implements IUsusModelStatus {

        private final Date lastTestRun;
        private final boolean success;
        private final Date lastComputationRun;

        DummyStatus( boolean success, Date lastTestRun, Date lastComputationRun ) {
            this.success = success;
            this.lastTestRun = lastTestRun;
            this.lastComputationRun = lastComputationRun;
        }

        public Date getLastComputerRun() {
            return lastComputationRun;
        }

        public Date getLastTestRun() {
            return lastTestRun;
        }

        public boolean isLastComputationRunSuccessful() {
            return success;
        }

        public boolean isStale() {
            return false;
        }

    }

}
