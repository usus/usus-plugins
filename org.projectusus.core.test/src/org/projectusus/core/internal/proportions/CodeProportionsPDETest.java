// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CodeProportionsPDETest {

    private UsusModel model;

    @Before
    public void setUp() {
        model = (UsusModel)UsusModel.getInstance();
    }

    @After
    public void tearDown() {
        model.dispose();
    }

    @Test
    public void listenerHandling() {
        DummyCodeProportionsListener listener = new DummyCodeProportionsListener();
        model.addUsusModelListener( listener );
        
        model.updateLastComputerRun();
        assertEquals(1, listener.getCallCount());
        
        model.removeUsusModelListener( listener );
        model.updateLastComputerRun();
        // no more calls
        assertEquals(1, listener.getCallCount());
    }

    private final class DummyCodeProportionsListener implements IUsusModelListener {
        private int callCount;

        public void ususModelChanged( IUsusModelStatus lastStatus, List<CodeProportion> entries ) {
          callCount++;
        }
        
        public int getCallCount() {
            return callCount;
        }
    }
}
