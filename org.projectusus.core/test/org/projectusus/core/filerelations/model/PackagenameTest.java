package org.projectusus.core.filerelations.model;

import static org.junit.Assert.assertSame;

import org.junit.Test;

public class PackagenameTest {

    @Test
    public void packagenameFactoryProduziertIdentischeObjekte() {
        String name = "my.nice.package"; //$NON-NLS-1$
        Packagename package1 = PackagenameFactory.get( name );
        Packagename package2 = PackagenameFactory.get( name );
        assertSame( package1, package2 );
    }

}
