package org.projectusus.core.filerelations.model.test;

import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.projectusus.core.filerelations.model.Packagename;

public class PackagenameTest {

    @Test
    public void packagenameFactoryProduziertIdentischeObjekte() {
        String name = "my.nice.package"; //$NON-NLS-1$
        Packagename package1 = Packagename.of( name, null );
        Packagename package2 = Packagename.of( name, null );
        assertSame( package1, package2 );
    }

}
