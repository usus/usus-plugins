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

public class UsusModelStatusTest {

    private List<CodeProportion> entries = new ArrayList<CodeProportion>();

    @Test
    public void isInitiallyStale() {
        assertTrue( new UsusModelStatus().isStale() );
    }

    @Test
    public void isStaleAfterSimpleRun() {
        ComputationRunModelUpdate computationRun = new ComputationRunModelUpdate( entries, true );
        UsusModelStatus status = new UsusModelStatus( computationRun, null );
        assertTrue( status.isStale() );
    }

    @Test
    public void isStaleAfterTestRunOnly() {
        UsusModelStatus status = new UsusModelStatus( null, new TestRunModelUpdate( null ) );
        assertFalse( status.isStale() );
    }

    @Test
    public void isStaleAfterUnsuccessfulRun() {
        ComputationRunModelUpdate computation = new ComputationRunModelUpdate( entries, false );
        UsusModelStatus status = new UsusModelStatus( computation, new TestRunModelUpdate( null ) );
        assertFalse( status.isStale() );
    }

    @Test
    public void isNotStaleAfterEverythingRan() {
        ComputationRunModelUpdate computation = new ComputationRunModelUpdate( entries, true );
        UsusModelStatus status = new UsusModelStatus( computation, new TestRunModelUpdate( null ) );
        assertFalse( status.isStale() );
    }
}
