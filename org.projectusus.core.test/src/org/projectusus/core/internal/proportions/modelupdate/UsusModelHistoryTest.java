// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.modelupdate;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.projectusus.core.internal.proportions.sqi.IsisMetrics.CC;
import static org.projectusus.core.internal.proportions.sqi.IsisMetrics.KG;
import static org.projectusus.core.internal.proportions.sqi.IsisMetrics.ML;
import static org.projectusus.core.internal.proportions.sqi.IsisMetrics.TA;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.projectusus.core.internal.proportions.CodeProportion;

public class UsusModelHistoryTest {

    @Test
    public void add() {
        UsusModelHistory history = new UsusModelHistory();
        history.add( new ComputationRunModelUpdate( emptyEntries(), true ) );
        assertTrue( history.getLastStatus().isLastComputationRunSuccessful() );

        history.add( new ComputationRunModelUpdate( emptyEntries(), false ) );
        assertFalse( history.getLastStatus().isLastComputationRunSuccessful() );
    }
    
    @Test
    public void entriesInCheckpoint() {
        UsusModelHistory history = new UsusModelHistory();
        history.add( new ComputationRunModelUpdate( dummyEntriesCp(), true ) );
        history.add( new TestRunModelUpdate( dummyEntryTa() ) );
        assertEquals( 1, history.getCheckpoints().size() );
        assertEquals( 4, history.getCheckpoints().get( 0 ).getEntries().size() );
    }

    @Test
    public void statusStaleAfterUnsuccessfulRun() {
        UsusModelHistory history = new UsusModelHistory();
        history.add( new TestRunModelUpdate( null ) );
        history.add( new ComputationRunModelUpdate( new ArrayList<CodeProportion>(), false ) );
        history.add( new TestRunModelUpdate( null ) );
        assertFalse( history.getLastStatus().isStale() );
    }

    private List<CodeProportion> emptyEntries() {
        return new ArrayList<CodeProportion>();
    }
    
    private CodeProportion dummyEntryTa() {
        return new CodeProportion( TA, 10, 100, 10.0);
    }
    
    private List<CodeProportion> dummyEntriesCp() {
        CodeProportion kg = new CodeProportion( KG, 3, 10, 12.0);
        CodeProportion cc = new CodeProportion( CC, 10, 42, 5.0);
        CodeProportion ml = new CodeProportion( ML, 42, 42, 100.0);
        return asList( kg, ml, cc );
    }

}
