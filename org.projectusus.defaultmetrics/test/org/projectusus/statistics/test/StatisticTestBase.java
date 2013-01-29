package org.projectusus.statistics.test;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.CockpitExtension;

public class StatisticTestBase {

    protected SourceCodeLocation location = new SourceCodeLocation( "location", 0, 0 );
    protected MetricsResults results = new MetricsResults();
    protected CockpitExtension statistic;
    protected String metricsLabel;

    protected void inspectMethodMetricsValue( int value ) {
        results.put( metricsLabel, value );
        statistic.inspectMethod( location, results );
    }

    protected void inspectClassMetricsValue( int value ) {
        results.put( metricsLabel, value );
        statistic.inspectClass( location, results );
    }

}
