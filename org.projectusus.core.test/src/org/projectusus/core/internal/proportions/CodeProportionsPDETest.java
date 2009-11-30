// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.internal.proportions.model.CodeProportion;
import org.projectusus.core.internal.proportions.model.IUsusElement;
import org.projectusus.core.internal.proportions.modelupdate.ComputationRunModelUpdate;
import org.projectusus.core.internal.proportions.modelupdate.IUsusModelHistory;

public class CodeProportionsPDETest {

    private UsusModel model;

    @Before
    public void setUp() {
        model = (UsusModel)UsusModel.getUsusModel();
    }

    @After
    public void tearDown() {
        model.dispose();
    }

    @Test
    public void listenerHandling() throws InterruptedException {
        DummyCodeProportionsListener listener = new DummyCodeProportionsListener();
        model.addUsusModelListener( listener );
        
        model.update( new ComputationRunModelUpdate( new ArrayList<CodeProportion>(), true ) );
        assertEquals(1, listener.getCallCount());
        
        model.removeUsusModelListener( listener );
        model.update( new ComputationRunModelUpdate( new ArrayList<CodeProportion>(), true ) );
        // no more calls
        assertEquals(1, listener.getCallCount());
    }

    private final class DummyCodeProportionsListener implements IUsusModelListener {
        private int callCount;

        public void ususModelChanged( IUsusModelHistory history, List<IUsusElement> elements ) {
          callCount++;
        }
        
        public int getCallCount() {
            return callCount;
        }
    }
}
