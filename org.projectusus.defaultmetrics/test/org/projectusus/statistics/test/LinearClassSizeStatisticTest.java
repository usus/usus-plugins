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
import org.projectusus.statistics.LinearClassSizeStatistic;

public class LinearClassSizeStatisticTest extends StatisticTestBase {

    @Before
    public void setup() {
        metricsLabel = MetricsResults.METHODS;
        statistic = new LinearClassSizeStatistic();
    }

    @Test
    public void constants() {
        assertThat( statistic.getLabel(), is( "Class size" ) );
        assertThat( ((LinearClassSizeStatistic)statistic).KG_LIMIT, is( 12 ) );
    }

    @Test
    public void noClassesYieldAllValues0() {
        assertThat( statistic.getBasis(), is( 0 ) );
        assertThat( statistic.getMetricsSum(), is( 0 ) );

        assertThat( statistic.getViolations(), is( 0 ) );
        assertThat( statistic.getHotspots(), is( empty() ) );

        assertThat( statistic.getAverage(), is( 0.0 ) );
    }

    @Test
    public void oneClassWithKG1YieldsNoHotspot() {
        inspectClassMetricsValue( 1 );

        assertThat( statistic.getBasis(), is( 1 ) );
        assertThat( statistic.getMetricsSum(), is( 1 ) );

        assertThat( statistic.getViolations(), is( 0 ) );
        assertThat( statistic.getHotspots(), is( empty() ) );

        assertThat( statistic.getAverage(), is( 0.0 ) );
    }

    @Test
    public void oneClassWithKG12YieldsNoHotspot() {
        inspectClassMetricsValue( 12 );

        assertThat( statistic.getBasis(), is( 1 ) );
        assertThat( statistic.getMetricsSum(), is( 12 ) );

        assertThat( statistic.getViolations(), is( 0 ) );
        assertThat( statistic.getHotspots(), is( empty() ) );

        assertThat( statistic.getAverage(), is( 0.0 ) );
    }

    @Test
    public void oneClassWithKG13YieldsOneHotspot() {
        inspectClassMetricsValue( 13 );

        assertThat( statistic.getBasis(), is( 1 ) );
        assertThat( statistic.getMetricsSum(), is( 13 ) );

        assertThat( statistic.getViolations(), is( 1 ) );
        assertThat( statistic.getHotspots().size(), is( 1 ) );
        assertThat( statistic.getHotspots().get( 0 ).getMetricsValue(), is( 13 ) );

        assertThat( statistic.getAverage(), is( closeTo( 8.3333, 0.001 ) ) );
    }

    @Test
    public void twoClassesWithKG1And13YieldOneHotspot() {
        inspectClassMetricsValue( 1 );
        inspectClassMetricsValue( 13 );

        assertThat( statistic.getBasis(), is( 2 ) );
        assertThat( statistic.getMetricsSum(), is( 14 ) );

        assertThat( statistic.getViolations(), is( 1 ) );
        assertThat( statistic.getHotspots().size(), is( 1 ) );
        assertThat( statistic.getHotspots().get( 0 ).getMetricsValue(), is( 13 ) );

        assertThat( statistic.getAverage(), is( closeTo( 4.1666, 0.001 ) ) );
    }

    @Test
    public void twoClassesWithKG13And16YieldTwoHotspots() {
        inspectClassMetricsValue( 13 );
        inspectClassMetricsValue( 16 );

        assertThat( statistic.getBasis(), is( 2 ) );
        assertThat( statistic.getMetricsSum(), is( 29 ) );

        assertThat( statistic.getViolations(), is( 2 ) );
        assertThat( statistic.getHotspots().size(), is( 2 ) );
        assertThat( statistic.getHotspots().get( 0 ).getMetricsValue(), is( 13 ) );
        assertThat( statistic.getHotspots().get( 1 ).getMetricsValue(), is( 16 ) );

        assertThat( statistic.getAverage(), is( closeTo( 20.8333, 0.001 ) ) );
    }
}
