// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

import static org.junit.Assert.*;

import org.junit.Test;

public class UsusModelStatusTest {

    @Test
    public void isInitiallyStale() {
        assertTrue( new UsusModelStatus().isStale());
    }
    
    @Test
    public void isStaleAfterSimpleRun() {
        UsusModelStatus status = new UsusModelStatus();
        status.updateLastComputerRun();
        status.setLastComputationRunSuccessful( true );
        assertTrue( status.isStale());
    }
    
    @Test
    public void isStaleAfterTestRunOnly() {
        UsusModelStatus status = new UsusModelStatus();
        status.updateLastTestRun();
        assertFalse( status.isStale());
    }
    
    @Test
    public void isStaleAfterUnsuccessfulRun() {
        UsusModelStatus status = new UsusModelStatus();
        status.updateLastComputerRun();
        status.setLastComputationRunSuccessful( false );
        status.updateLastTestRun();
        assertFalse( status.isStale());
    }

    @Test
    public void remainsStaleAfterUnsuccessfulRun() {
        UsusModelStatus status = new UsusModelStatus();
        status.updateLastTestRun();
        status.updateLastComputerRun();
        status.setLastComputationRunSuccessful( false );
        status.updateLastTestRun();
        assertFalse( status.isStale());
    }
    
    @Test
    public void isNotStaleAfterEverythingRan() {
        UsusModelStatus status = new UsusModelStatus();
        status.updateLastComputerRun();
        status.updateLastTestRun();
        assertFalse( status.isStale());
    }
}
