package org.projectusus.core.filerelations.model;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;

import org.junit.Test;

public class PackageCycleTest {

    @Test( expected = IllegalArgumentException.class )
    public void nullPackageCycle() {
        new Cycle<Object>( null );
    }

    @Test( expected = IllegalArgumentException.class )
    public void emptyPackageCycle() {
        new Cycle<Packagename>( new HashSet<Packagename>() );
    }

    @Test( expected = IllegalArgumentException.class )
    public void packageCycleWith1Package() {
        HashSet<Packagename> packages = new HashSet<Packagename>();
        packages.add( Packagename.of( "package1" ) ); //$NON-NLS-1$
        new Cycle<Packagename>( packages );
    }

    @Test
    public void packageCycleWith2Packages() {
        HashSet<Packagename> packages = new HashSet<Packagename>();
        packages.add( Packagename.of( "package1" ) ); //$NON-NLS-1$
        packages.add( Packagename.of( "package2" ) ); //$NON-NLS-1$
        Cycle<Packagename> cycle = new Cycle<Packagename>( packages );

        assertEquals( 2, cycle.numberOfElements() );
    }
}
