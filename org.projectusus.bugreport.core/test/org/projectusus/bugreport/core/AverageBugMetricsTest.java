// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.bugreport.core;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class AverageBugMetricsTest {

    private AverageBugMetrics averageBugMetrics;

    @Before
    public void setUp() {
        averageBugMetrics = new AverageBugMetrics();
    }

    @Test
    public void constructor() {
        assertEquals( Double.NaN, averageBugMetrics.getAverageCyclomaticComplexity(), 0.0 );
        assertEquals( Double.NaN, averageBugMetrics.getAverageMethodLength(), 0.0 );
        assertEquals( Double.NaN, averageBugMetrics.getAverageNumberOfMethodsInClass(), 0.0 );
    }

    @Test
    public void testAddBugMetrics() {
        BugMetrics bugMetrics = new BugMetrics();
        bugMetrics.setCyclomaticComplexity( 2 );
        bugMetrics.setMethodLength( 3 );
        bugMetrics.setNumberOfMethods( 4 );

        averageBugMetrics.addBugMetrics( bugMetrics );

        assertEquals( 2, averageBugMetrics.getAverageCyclomaticComplexity(), 0.0 );
        assertEquals( 3, averageBugMetrics.getAverageMethodLength(), 0.0 );
        assertEquals( 3, averageBugMetrics.getAverageMethodLength(), 0.0 );
        assertEquals( 4, averageBugMetrics.getAverageNumberOfMethodsInClass(), 0.0 );

    }

}
