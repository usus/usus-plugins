// Copyright (c) 2005-2006, 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.yellowcount;

import static org.junit.Assert.*;

import org.junit.Test;

public class WiseCrackTest {

    @Test
    public void testToString() {
        assertEquals( "", new WiseCrack(0).toString());
        assertEquals( "That's cool.", new WiseCrack(42).toString());
        assertEquals( "That's a shame.", new WiseCrack(1).toString());
        assertEquals( "That's a shame.", new WiseCrack(43).toString());
    }
}
