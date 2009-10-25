// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.modelupdate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.projectusus.core.internal.proportions.CodeProportion;

public class UsusModelHistoryTest {

    @Test
    public void add() {
        UsusModelHistory history = new UsusModelHistory();
        history.add( new ComputationRunModelUpdate( dummyEntries(), true ) );
        assertTrue( history.getLastStatus().isLastComputationRunSuccessful() );

        history.add( new ComputationRunModelUpdate( dummyEntries(), false ) );
        assertFalse( history.getLastStatus().isLastComputationRunSuccessful() );
    }

    @Test
    public void statusStaleAfterUnsuccessfulRun() {
        UsusModelHistory history = new UsusModelHistory();
        history.add( new TestRunModelUpdate( null ) );
        history.add( new ComputationRunModelUpdate( new ArrayList<CodeProportion>(), false ) );
        history.add( new TestRunModelUpdate( null ) );
        assertFalse( history.getLastStatus().isStale() );
    }

    private List<CodeProportion> dummyEntries() {
        return new ArrayList<CodeProportion>();
    }
}
