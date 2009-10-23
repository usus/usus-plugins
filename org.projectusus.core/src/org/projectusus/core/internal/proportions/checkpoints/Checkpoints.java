package org.projectusus.core.internal.proportions.checkpoints;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.projectusus.core.internal.proportions.CodeProportion;
import org.projectusus.core.internal.proportions.IUsusModel;
import org.projectusus.core.internal.proportions.IUsusModelListener;
import org.projectusus.core.internal.proportions.IUsusModelStatus;
import org.projectusus.core.internal.proportions.model.IUsusElement;

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
            public void ususModelChanged( IUsusModelStatus lastStatus, List<IUsusElement> elements ) {
                createCheckpoint( lastStatus, cumulate( elements ) );
            }
        } );
    }

    @Override
    public String toString() {
        return "Checkpoints (" + checkpoints.size() + ")"; //$NON-NLS-1$//$NON-NLS-2$
    }

    private boolean canCreate( IUsusModelStatus status ) {
        return lastUpdateWasTestRun( status ) && computedBetweenLastTestRuns( status );
    }

    private boolean computedBetweenLastTestRuns( IUsusModelStatus status ) {
        boolean result = true;
        if( !checkpoints.isEmpty() ) {
            ICheckpoint lastCheckPoint = checkpoints.get( checkpoints.size() - 1 );
            Date time = lastCheckPoint.getTime();
            result = time.before( status.getLastTestRun() );
        }
        return result;
    }

    private boolean lastUpdateWasTestRun( IUsusModelStatus status ) {
        return status.isLastComputationRunSuccessful() && status.getLastTestRun() != null;
    }

    private List<CodeProportion> cumulate( List<IUsusElement> elements ) {
        List<CodeProportion> result = new ArrayList<CodeProportion>();
        for( IUsusElement element : elements ) {
            result.addAll( element.getEntries() );
        }
        return result;
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

        @Override
        public String toString() {
            return "Checkpoint " + time + " (" + entries.size() + " entries)"; //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
        }
    }
}
