// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.modelupdate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.projectusus.core.internal.proportions.CodeProportion;

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