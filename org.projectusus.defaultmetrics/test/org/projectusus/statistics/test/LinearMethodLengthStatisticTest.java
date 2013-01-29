package org.projectusus.statistics.test;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.projectusus.core.testutil.PrimitiveAssert.assertThat;
import static org.projectusus.core.testutil.PrimitiveMatchers.is;

import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.statistics.LinearMethodLengthStatistic;

public class LinearMethodLengthStatisticTest extends StatisticTestBase {

    @Before
    public void setup() {
        metricsLabel = MetricsResults.ML;
        statistic = new LinearMethodLengthStatistic();
    }

    @Test
    public void constants() {
        assertThat( statistic.getLabel(), is( "Method length" ) );
        assertThat( ((LinearMethodLengthStatistic)statistic).ML_LIMIT, is( 9 ) );
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
    public void oneMethodWithML1YieldsNoHotspot() {
        inspectMethodMetricsValue( 1 );

        assertThat( statistic.getBasis(), is( 1 ) );
        assertThat( statistic.getMetricsSum(), is( 1 ) );

        assertThat( statistic.getViolations(), is( 0 ) );
        assertThat( statistic.getHotspots(), is( empty() ) );

        assertThat( statistic.getAverage(), is( 0.0 ) );
    }

    @Test
    public void oneMethodWithML9YieldsNoHotspot() {
        inspectMethodMetricsValue( 9 );

        assertThat( statistic.getBasis(), is( 1 ) );
        assertThat( statistic.getMetricsSum(), is( 9 ) );

        assertThat( statistic.getViolations(), is( 0 ) );
        assertThat( statistic.getHotspots(), is( empty() ) );

        assertThat( statistic.getAverage(), is( 0.0 ) );
    }

    @Test
    public void oneMethodWithML10YieldsOneHotspot() {
        inspectMethodMetricsValue( 10 );

        assertThat( statistic.getBasis(), is( 1 ) );
        assertThat( statistic.getMetricsSum(), is( 10 ) );

        assertThat( statistic.getViolations(), is( 1 ) );
        assertThat( statistic.getHotspots().size(), is( 1 ) );
        assertThat( statistic.getHotspots().get( 0 ).getMetricsValue(), is( 10 ) );

        assertThat( statistic.getAverage(), is( closeTo( 11.1111, 0.001 ) ) );
    }

    @Test
    public void twoMethodsWithML1And10YieldOneHotspot() {
        inspectMethodMetricsValue( 1 );
        inspectMethodMetricsValue( 10 );

        assertThat( statistic.getBasis(), is( 2 ) );
        assertThat( statistic.getMetricsSum(), is( 11 ) );

        assertThat( statistic.getViolations(), is( 1 ) );
        assertThat( statistic.getHotspots().size(), is( 1 ) );
        assertThat( statistic.getHotspots().get( 0 ).getMetricsValue(), is( 10 ) );

        assertThat( statistic.getAverage(), is( closeTo( 5.5555, 0.001 ) ) );
    }

    @Test
    public void twoMethodsWithML12And15YieldTwoHotspots() {
        inspectMethodMetricsValue( 12 );
        inspectMethodMetricsValue( 15 );

        assertThat( statistic.getBasis(), is( 2 ) );
        assertThat( statistic.getMetricsSum(), is( 27 ) );

        assertThat( statistic.getViolations(), is( 2 ) );
        assertThat( statistic.getHotspots().size(), is( 2 ) );
        assertThat( statistic.getHotspots().get( 0 ).getMetricsValue(), is( 12 ) );
        assertThat( statistic.getHotspots().get( 1 ).getMetricsValue(), is( 15 ) );

        assertThat( statistic.getAverage(), is( 50.0 ) );
    }
}
