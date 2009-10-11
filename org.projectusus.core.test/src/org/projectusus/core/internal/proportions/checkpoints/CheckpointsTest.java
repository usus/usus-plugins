package org.projectusus.core.internal.proportions.checkpoints;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;
import org.projectusus.core.internal.proportions.CodeProportion;
import org.projectusus.core.internal.proportions.IUsusModelStatus;


public class CheckpointsTest {

    @Test
    public void noCreateWhenUnsuccessful() {
        Checkpoints checkpoints = new Checkpoints();
        IUsusModelStatus status = new DummyStatus(false, new Date(), new Date());
        checkpoints.createCheckpoint( status, new ArrayList<CodeProportion>() );
        
        assertEquals( 0, checkpoints.getCheckpoints().size());
    }
    
    @Test
    public void noCreateWhenNoTestRunDate() {
        Checkpoints checkpoints = new Checkpoints();
        IUsusModelStatus status = new DummyStatus(true, null, null);
        checkpoints.createCheckpoint( status, new ArrayList<CodeProportion>() );
        
        assertEquals( 0, checkpoints.getCheckpoints().size());
    }
    
    @Test
    public void noCreateWhenBetweenComputations() {
        Checkpoints checkpoints = new Checkpoints();
        ArrayList<CodeProportion> entries = new ArrayList<CodeProportion>();
        
        Date testRun = new Date();
        checkpoints.createCheckpoint( new DummyStatus(true, new Date(), testRun), entries );
        assertEquals( 1, checkpoints.getCheckpoints().size());

        Date firstComputationRun = new Date( testRun.getTime() + 1000);
        checkpoints.createCheckpoint( new DummyStatus(true, testRun, firstComputationRun), entries );
        // no new checkpoint created if no test run, just computations
        assertEquals( 1, checkpoints.getCheckpoints().size());
    }
    
    @Test
    public void create() {
        Checkpoints checkpoints = new Checkpoints();
        IUsusModelStatus status = new DummyStatus(true, new Date(), null);
        checkpoints.createCheckpoint( status, new ArrayList<CodeProportion>() );
        
        assertEquals( 1, checkpoints.getCheckpoints().size());
        // but no entry
        assertEquals(0, checkpoints.getCheckpoints().get( 0 ).getEntries().size());
    }
    
    
    private class DummyStatus implements IUsusModelStatus {

        private final Date lastTestRun;
        private final boolean success;
        private final Date lastComputationRun;

        DummyStatus(boolean success, Date lastTestRun, Date lastComputationRun){
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

    }

}
