// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.bugreport;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class BugMetricsTest {

    private BugMetrics bugMetrics;

    @Before
    public void setUp() {
        bugMetrics = new BugMetrics();
    }
    
    @Test
    public void constructor() throws Exception {
        assertEquals(0, bugMetrics.getCyclomaticComplexity());
        assertEquals(0, bugMetrics.getNumberOfMethods());
        assertEquals(0, bugMetrics.getMethodLength());
    }
    
    @Test
    public void testAdd() {
        BugMetrics other = new BugMetrics();
        other.setCyclomaticComplexity( 4 );
        other.setNumberOfMethods( 2 );
        other.setMethodLength( 7 );
        bugMetrics.add( other );
        
        assertEquals(4, bugMetrics.getCyclomaticComplexity());
        assertEquals(2, bugMetrics.getNumberOfMethods());
        assertEquals(7, bugMetrics.getMethodLength());
    }

}
