package org.projectusus.core.filerelations.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.projectusus.core.filerelations.model.Cycle;
import org.projectusus.core.filerelations.model.Packagename;

public class PackageCycleTest {

    @Test
    public void nullPackageCycle() {
        try {
            new Cycle<Object>( null );
        } catch( IllegalArgumentException e ) {
            // this exception is thrown by the Cycle constructor
            return;
        } catch( AssertionError e ) {
            // this exception is thrown by the Contract if C4J is enabled
            return;
        }
        fail( "Cycle with null argument should throw an exception" );
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
