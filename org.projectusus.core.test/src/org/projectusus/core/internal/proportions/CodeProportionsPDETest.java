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

    private CodeProportions model;

    @Before
    public void setUp() {
        model = (CodeProportions)CodeProportions.getInstance();
    }

    @After
    public void tearDown() {
        model.dispose();
    }

    @Test
    public void listenerHandling() {
        DummyCodeProportionsListener listener = new DummyCodeProportionsListener();
        model.addCodeProportionsListener( listener );
        
        model.updateLastComputerRun();
        assertEquals(1, listener.getCallCount());
        
        model.removeCodeProportionsListener( listener );
        model.updateLastComputerRun();
        // no more calls
        assertEquals(1, listener.getCallCount());
    }

    private final class DummyCodeProportionsListener implements ICodeProportionsListener {
        private int callCount;

        public void codeProportionsChanged( ICodeProportionsStatus lastStatus, List<CodeProportion> entries ) {
          callCount++;
        }
        
        public int getCallCount() {
            return callCount;
        }
    }
}
