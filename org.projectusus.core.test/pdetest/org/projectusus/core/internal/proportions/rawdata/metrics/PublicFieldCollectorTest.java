package org.projectusus.core.internal.proportions.rawdata.metrics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.internal.proportions.rawdata.PDETestForMetricsComputation;
import org.projectusus.core.statistics.DefaultCockpitExtension;
import org.projectusus.metrics.PublicFieldCollector;

public class PublicFieldCollectorTest extends PDETestForMetricsComputation {

    private DefaultCockpitExtension statistic = getClassValidator( PublicFieldCollector.PUBLIC_FIELDS );

    @Test
    public void countNoPublicFields() throws Exception {
        // createWSFolder( "publicfields", project1 );
        // createJavaWSFile( "publicfields/NoPublicFields.java" );
        buildFullyAndWait();
        // assertEquals( 1, getNumberOfClasses() );

        statistic.visit();
        assertEquals( 0, statistic.getMetricsSum() );

    }

    @Test
    public void countPublicFields() throws Exception {
        createWSFolder( "publicfields", project1 );
        createJavaWSFile( "publicfields/OneOfEachKind.java" );
        buildFullyAndWait();
        assertEquals( 1, getNumberOfClasses() );

        statistic.visit();
        assertEquals( 4, statistic.getMetricsSum() );

    }

    private DefaultCockpitExtension getClassValidator( final String valueName ) {
        return new DefaultCockpitExtension( "", 0 ) {
            @Override
            public void inspectClass( SourceCodeLocation location, MetricsResults results ) {
                addViolation( location, results.get( valueName ).intValue() );
            }

            @Override
            protected String getDescription() {
                return null;
            }
        };
    }

    @Test
    public void doubleNaNIsEvil() throws Exception {
        assertEquals( 0, (int)Double.NaN );
    }
}
