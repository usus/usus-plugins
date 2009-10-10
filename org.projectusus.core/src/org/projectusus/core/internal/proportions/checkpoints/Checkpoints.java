package org.projectusus.core.internal.proportions.checkpoints;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.projectusus.core.internal.proportions.CodeProportion;
import org.projectusus.core.internal.proportions.ICodeProportions;
import org.projectusus.core.internal.proportions.ICodeProportionsListener;
import org.projectusus.core.internal.proportions.ICodeProportionsStatus;

public class Checkpoints {

    List<ICheckpoint> checkpoints = new ArrayList<ICheckpoint>();

    public void createCheckpoint( ICodeProportionsStatus status, List<CodeProportion> entries ) {
        if( canCreate( status ) ) {
            checkpoints.add( new Checkpoint( status.getLastTestRun(), entries ) );
        }
    }

    public List<ICheckpoint> getCheckpoints() {
        return checkpoints;
    }

    public void connect( ICodeProportions codeProportions ) {
        codeProportions.addCodeProportionsListener( new ICodeProportionsListener() {
            public void codeProportionsChanged( ICodeProportionsStatus lastStatus, List<CodeProportion> entries ) {
                createCheckpoint( lastStatus, entries );
            }
        } );
    }

    private boolean canCreate( ICodeProportionsStatus status ) {
        return lastUpdateWasTestRun( status ) && computedBetweenLastTestRuns( status );
    }

    private boolean computedBetweenLastTestRuns( ICodeProportionsStatus status ) {
        boolean result = true;
        if( !checkpoints.isEmpty() ) {
            ICheckpoint lastCheckPoint = checkpoints.get( checkpoints.size() - 1 );
            Date time = lastCheckPoint.getTime();
            result = time.before( status.getLastComputerRun() );
        }
        return result;
    }

    private boolean lastUpdateWasTestRun( ICodeProportionsStatus status ) {
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
