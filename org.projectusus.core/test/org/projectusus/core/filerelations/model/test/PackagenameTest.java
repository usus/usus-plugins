package org.projectusus.core.filerelations.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.projectusus.core.filerelations.model.Packagename;

public class PackagenameTest {

    private static final String PACKAGENAME_STRING = "packagename";

    private Packagename packagename1 = Packagename.of( PACKAGENAME_STRING, null );
    private Packagename packagename2 = Packagename.of( PACKAGENAME_STRING, null );

    @Test
    public void differentPackagenamesWithTheSameStringAreEqualAndSame() {
        assertEquals( packagename1, packagename2 );
        assertSame( packagename1, packagename2 );
    }

}
