package org.projectusus.core.filerelations.model;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

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
        Set<Packagename> packages = new HashSet<Packagename>();
        packages.add( Packagename.of( "package1", null ) ); //$NON-NLS-1$
        new Cycle<Packagename>( packages );
    }

    @Test
    public void packageCycleWith2Packages() {
        Set<Packagename> packages = new HashSet<Packagename>();
        packages.add( Packagename.of( "package1", null ) ); //$NON-NLS-1$
        packages.add( Packagename.of( "package2", null ) ); //$NON-NLS-1$
        Cycle<Packagename> cycle = new Cycle<Packagename>( packages );

        assertEquals( 2, cycle.numberOfElements() );
    }
}
