package org.projectusus.core.statistics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.projectusus.core.basis.SourceCodeLocation;

public class CockpitExtensionTest {

    class CockpitExtensionStub extends CockpitExtension {

        public CockpitExtensionStub() {
            super( "# of WTFs/minute", 0 ); //$NON-NLS-1$
        }

        @Override
        protected String hotspotsAreUnits() {
            return "";
        }
    }

    @Test
    public void test() {
        SourceCodeLocation location = new SourceCodeLocation( "murks", 0, 1 ); //$NON-NLS-1$
        CockpitExtensionStub extension = new CockpitExtensionStub();
        extension.addResult( location, 1 );

        assertEquals( 1, extension.getHistogram().countOf( 1 ) );
    }

}
