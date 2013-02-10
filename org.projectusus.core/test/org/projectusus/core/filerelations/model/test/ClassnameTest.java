package org.projectusus.core.filerelations.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.Test;
import org.projectusus.core.filerelations.model.Classname;

public class ClassnameTest {
    private static final String NAME_STRING = "name";

    private Classname classname1 = new Classname( NAME_STRING );
    private Classname classname2 = new Classname( NAME_STRING );

    @Test
    public void differentClassnamesWithTheSameStringAreEqualAndNotSame() {
        assertEquals( classname1, classname2 );
        assertNotSame( classname1, classname2 );
    }

}
