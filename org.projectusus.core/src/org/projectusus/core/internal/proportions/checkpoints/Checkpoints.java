package org.projectusus.core.internal.proportions.checkpoints;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.projectusus.core.internal.proportions.CodeProportion;
import org.projectusus.core.internal.proportions.IUsusModel;
import org.projectusus.core.internal.proportions.IUsusModelListener;
import org.projectusus.core.internal.proportions.IUsusModelStatus;

public class Checkpoints {

    List<ICheckpoint> checkpoints = new ArrayList<ICheckpoint>();

    public void createCheckpoint( IUsusModelStatus status, List<CodeProportion> entries ) {
        if( canCreate( status ) ) {
            checkpoints.add( new Checkpoint( status.getLastTestRun(), entries ) );
        }
    }

    public List<ICheckpoint> getCheckpoints() {
        return checkpoints;
    }

    public void connect( IUsusModel codeProportions ) {
        codeProportions.addUsusModelListener( new IUsusModelListener() {
            public void ususModelChanged( IUsusModelStatus lastStatus, List<CodeProportion> entries ) {
                createCheckpoint( lastStatus, entries );
            }
        } );
    }

    private boolean canCreate( IUsusModelStatus status ) {
        return lastUpdateWasTestRun( status ) && computedBetweenLastTestRuns( status );
    }

    private boolean computedBetweenLastTestRuns( IUsusModelStatus status ) {
        boolean result = true;
        if( !checkpoints.isEmpty() ) {
            ICheckpoint lastCheckPoint = checkpoints.get( checkpoints.size() - 1 );
            Date time = lastCheckPoint.getTime();
            result = time.before( status.getLastComputerRun() );
        }
        return result;
    }

    private boolean lastUpdateWasTestRun( IUsusModelStatus status ) {
        return status.isLastComputationRunSuccessful() && status.getLastTestRun() != null;
    }

    private static class Checkpoint implements ICheckpoint {
        private final Date time;
        private final List<CodeProportion> entries;

        Checkpoint( Date time, List<CodeProportion> entries ) {
            this.time = time;
            this.entries = entries;
        }

        public Date getTime() {
            return time;
        }

        public List<CodeProportion> getEntries() {
            return entries;
        }
    }
}
