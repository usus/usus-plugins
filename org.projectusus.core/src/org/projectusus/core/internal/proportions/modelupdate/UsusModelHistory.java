// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.modelupdate;

import static java.util.Collections.unmodifiableList;
import static org.projectusus.core.internal.proportions.modelupdate.IUsusModelUpdate.Type.COMPUTATION_RUN;
import static org.projectusus.core.internal.proportions.modelupdate.IUsusModelUpdate.Type.TEST_RUN;

import java.util.ArrayList;
import java.util.List;

import org.projectusus.core.internal.proportions.modelupdate.IUsusModelUpdate.Type;

public class UsusModelHistory implements IUsusModelHistory {

    private final Checkpoints checkpoints = new Checkpoints();
    private final List<IUsusModelUpdate> history = new ArrayList<IUsusModelUpdate>();
    private UsusModelStatus status = new UsusModelStatus();

    public void add( IUsusModelUpdate modelUpdate ) {
        history.add( modelUpdate );
        updateStatus( modelUpdate );
        createCheckpoint();
    }

    // interface
    // //////////

    public IUsusModelStatus getLastStatus() {
        return status;
    }

    public List<ICheckpoint> getCheckpoints() {
        return unmodifiableList( checkpoints.getCheckpoints() );
    }

    // internal
    // /////////

    private void updateStatus( IUsusModelUpdate modelUpdate ) {
        status = new UsusModelStatus( last( COMPUTATION_RUN ), last( TEST_RUN ) );
    }

    private void createCheckpoint() {
        if( canCreate() ) {
            checkpoints.createCheckpoint( last().getEntries() );
        }
    }

    private boolean canCreate() {
        return lastUpdateWasTestRun() && lastComputationRunWasSuccessful() && computedBetweenLastTestRuns();
    }

    private boolean lastComputationRunWasSuccessful() {
        return last( COMPUTATION_RUN ) != null && last( COMPUTATION_RUN ).isSuccessful();
    }

    private boolean lastUpdateWasTestRun() {
        return history.isEmpty() ? false : isTestRun( last() );
    }

    private boolean computedBetweenLastTestRuns() {
        boolean result = true;
        IUsusModelUpdate secondLastTestRun = findSecondLastTestRun();
        if( secondLastTestRun != null ) {
            result = existsLastSuccessfulComputationRunAfter( secondLastTestRun );
        }
        return result;
    }

    private boolean existsLastSuccessfulComputationRunAfter( IUsusModelUpdate start ) {
        IUsusModelUpdate result = null;
        boolean startEncountered = false;
        for( IUsusModelUpdate historyElement : history ) {
            startEncountered |= historyElement == start;
            if( startEncountered && isSuccessfulComputation( historyElement ) ) {
                result = historyElement;
            }
        }
        return result != null;
    }

    private boolean isSuccessfulComputation( IUsusModelUpdate historyElement ) {
        return historyElement.getType() == COMPUTATION_RUN && historyElement.isSuccessful();
    }

    private IUsusModelUpdate findSecondLastTestRun() {
        IUsusModelUpdate result = null;
        for( IUsusModelUpdate historyElement : history ) {
            if( isTestRun( historyElement ) && !historyElement.equals( last() ) ) {
                result = historyElement;
            }
        }
        return result;
    }

    private boolean isTestRun( IUsusModelUpdate historyElement ) {
        return historyElement.getType() == TEST_RUN;
    }

    private IUsusModelUpdate last( Type type ) {
        IUsusModelUpdate result = null;
        for( IUsusModelUpdate historyElement : history ) {
            if( historyElement.getType() == type ) {
                result = historyElement;
            }
        }
        return result;
    }

    private IUsusModelUpdate last() {
        return history.get( history.size() - 1 );
    }
}
