package org.projectusus.statistics.test;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.projectusus.core.testutil.PrimitiveAssert.assertThat;
import static org.projectusus.core.testutil.PrimitiveMatchers.is;

import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.statistics.LinearCyclomaticComplexityStatistic;

public class LinearCyclomaticComplexityStatisticTest extends StatisticTestBase {

    @Before
    public void setup() {
        metricsLabel = MetricsResults.CC;
        statistic = new LinearCyclomaticComplexityStatistic();
    }

    @Test
    public void constants() {
        assertThat( statistic.getLabel(), is( "Cyclomatic complexity" ) );
        assertThat( ((LinearCyclomaticComplexityStatistic)statistic).CC_LIMIT, is( 4 ) );
    }

    @Test
    public void noMethodsYieldAllValues0() {
        assertThat( statistic.getBasis(), is( 0 ) );
        assertThat( statistic.getMetricsSum(), is( 0 ) );

        assertThat( statistic.getViolations(), is( 0 ) );
        assertThat( statistic.getHotspots(), is( empty() ) );

        assertThat( statistic.getAverage(), is( 0.0 ) );
    }

    @Test
    public void oneMethodWithCC1YieldsNoHotspot() {
        inspectMethodMetricsValue( 1 );

        assertThat( statistic.getBasis(), is( 1 ) );
        assertThat( statistic.getMetricsSum(), is( 1 ) );

        assertThat( statistic.getViolations(), is( 0 ) );
        assertThat( statistic.getHotspots(), is( empty() ) );

        assertThat( statistic.getAverage(), is( 0.0 ) );
    }

    @Test
    public void oneMethodWithCC4YieldsNoHotspot() {
        inspectMethodMetricsValue( 4 );

        assertThat( statistic.getBasis(), is( 1 ) );
        assertThat( statistic.getMetricsSum(), is( 4 ) );

        assertThat( statistic.getViolations(), is( 0 ) );
        assertThat( statistic.getHotspots(), is( empty() ) );

        assertThat( statistic.getAverage(), is( 0.0 ) );
    }

    @Test
    public void oneMethodWithCC5YieldsOneHotspot() {
        inspectMethodMetricsValue( 5 );

        assertThat( statistic.getBasis(), is( 1 ) );
        assertThat( statistic.getMetricsSum(), is( 5 ) );

        assertThat( statistic.getViolations(), is( 1 ) );
        assertThat( statistic.getHotspots().size(), is( 1 ) );
        assertThat( statistic.getHotspots().get( 0 ).getMetricsValue(), is( 5 ) );

        assertThat( statistic.getAverage(), is( 25.0 ) );
    }

    @Test
    public void twoMethodsWithCC1And5YieldOneHotspot() {
        inspectMethodMetricsValue( 1 );
        inspectMethodMetricsValue( 5 );

        assertThat( statistic.getBasis(), is( 2 ) );
        assertThat( statistic.getMetricsSum(), is( 6 ) );

        assertThat( statistic.getViolations(), is( 1 ) );
        assertThat( statistic.getHotspots().size(), is( 1 ) );
        assertThat( statistic.getHotspots().get( 0 ).getMetricsValue(), is( 5 ) );

        assertThat( statistic.getAverage(), is( 12.5 ) );
    }

    @Test
    public void twoMethodsWithCC5And7YieldTwoHotspots() {
        inspectMethodMetricsValue( 5 );
        inspectMethodMetricsValue( 7 );

        assertThat( statistic.getBasis(), is( 2 ) );
        assertThat( statistic.getMetricsSum(), is( 12 ) );

        assertThat( statistic.getViolations(), is( 2 ) );
        assertThat( statistic.getHotspots().size(), is( 2 ) );
        assertThat( statistic.getHotspots().get( 0 ).getMetricsValue(), is( 5 ) );
        assertThat( statistic.getHotspots().get( 1 ).getMetricsValue(), is( 7 ) );

        assertThat( statistic.getAverage(), is( 50.0 ) );
    }
}
