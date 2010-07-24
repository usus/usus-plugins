package org.projectusus.core.filerelations.model;

import static org.junit.Assert.assertSame;

import org.junit.Test;

public class PackagenameTest {

    @Test
    public void packagenameFactoryProduziertIdentischeObjekte() {
        String name = "my.nice.package"; //$NON-NLS-1$
        Packagename package1 = Packagename.of( name, null );
        Packagename package2 = Packagename.of( name, null );
        assertSame( package1, package2 );
    }

}
