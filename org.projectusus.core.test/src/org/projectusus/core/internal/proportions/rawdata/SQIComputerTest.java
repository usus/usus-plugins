// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import static org.junit.Assert.assertEquals;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.ML;

import org.junit.Test;

public class SQIComputerTest {

    @Test
    public void zeroBasisYieldsZero() {
        SQIComputer computer = new SQIComputer( 0, 42, ML );
        assertEquals(0.0, computer.compute());
    }
    
    @Test
    public void noViolationsYields100Percent() {
        SQIComputer computer = new SQIComputer( 10, 0, ML );
        assertEquals( 100.0, computer.compute());
    }
}
