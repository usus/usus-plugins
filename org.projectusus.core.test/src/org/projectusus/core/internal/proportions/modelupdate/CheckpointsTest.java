// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.modelupdate;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.projectusus.core.internal.proportions.CodeProportion;

public class CheckpointsTest {

    private List<CodeProportion> entries = new ArrayList<CodeProportion>();

    @Test
    public void noCreateWithoutTestRun() {
        UsusModelHistory history = new UsusModelHistory();
        history.add( new ComputationRunModelUpdate( entries, false ) );
        assertEquals( 0, history.getCheckpoints().size() );
    }

    @Test
    public void noCreateWhenUnsuccessfulComputation() {
        UsusModelHistory history = new UsusModelHistory();
        history.add( new ComputationRunModelUpdate( entries, false ) );
        history.add( new TestRunModelUpdate( null ) );
        assertEquals( 0, history.getCheckpoints().size() );
    }
    
    @Test
    public void create() {
        UsusModelHistory history = new UsusModelHistory();
        history.add( new ComputationRunModelUpdate( entries, true ) );
        history.add( new TestRunModelUpdate( null ) );
        assertEquals( 1, history.getCheckpoints().size() );
        // but no entry
        assertEquals( 0, history.getCheckpoints().get( 0 ).getEntries().size() );
    }

    @Test
    public void noCreateWhenNoIntermediateComputation() {
        UsusModelHistory history = new UsusModelHistory();
        history.add( new ComputationRunModelUpdate( entries, true ) );
        history.add( new TestRunModelUpdate( null ) );
        // now we have both computation and test -> first checkpoint
        assertEquals( 1, history.getCheckpoints().size() );

        history.add( new TestRunModelUpdate( null ) );
        // no new checkpoint created if no computations, just another test run
        assertEquals( 1, history.getCheckpoints().size() );
    }

    @Test
    public void noCreateWhenBetweenComputations() {
        UsusModelHistory history = new UsusModelHistory();
        history.add( new ComputationRunModelUpdate( entries, true ) );
        history.add( new TestRunModelUpdate( null ) );
        // now we have both computation and test -> first checkpoint
        assertEquals( 1, history.getCheckpoints().size() );

        history.add( new ComputationRunModelUpdate( entries, true ) );
        // no new checkpoint created if no test run, just computations
        assertEquals( 1, history.getCheckpoints().size() );
    }
}
