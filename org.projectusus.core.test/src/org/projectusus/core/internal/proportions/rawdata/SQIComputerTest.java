// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.ML;

import org.junit.Test;

public class SQIComputerTest {

    @Test
    public void zeroBasisYieldsZero() {
        SQIComputer computer = new SQIComputer( 0, 42, ML );
        assertThat( computer.compute(), is( 0.0 ) );
    }
    
    @Test
    public void noViolationsYields100Percent() {
        SQIComputer computer = new SQIComputer( 10, 0, ML );
        assertThat( computer.compute(), is( 100.0 ) );
    }
}
