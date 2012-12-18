package org.projectusus.statistics.test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.projectusus.core.testutil.PrimitiveAssert.assertThat;
import static org.projectusus.core.testutil.PrimitiveMatchers.is;

import org.junit.Test;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.statistics.LinearCyclomaticComplexityStatistic;

public class LinearCyclomaticComplexityStatisticTest {

    private LinearCyclomaticComplexityStatistic statistic = new LinearCyclomaticComplexityStatistic();
    private SourceCodeLocation location = new SourceCodeLocation( "method", 0, 0 );
    private MetricsResults results = new MetricsResults();

    @Test
    public void constants() {
        assertThat( statistic.getLabel(), is( "Cyclomatic complexity" ) );
        assertThat( statistic.CC_LIMIT, is( 4 ) );
    }

    @Test
    public void noMethods() {
        assertThat( statistic.getAverage(), is( 0.0 ) );
        assertThat( statistic.getMetricsSum(), is( 0 ) );
        assertThat( statistic.getBasis(), is( 0 ) );
        assertThat( statistic.getHotspots().size(), is( 0 ) );
        assertThat( statistic.getViolations(), is( 0 ) );
    }

    @Test
    public void oneMethodWithCC1() {
        results.put( MetricsResults.CC, 1 );
        statistic.inspectMethod( location, results );

        assertThat( statistic.getAverage(), is( 0.0 ) );
        assertThat( statistic.getMetricsSum(), is( 1 ) );
        assertThat( statistic.getBasis(), is( 1 ) );
        assertThat( statistic.getHotspots().size(), is( 0 ) );
        assertThat( statistic.getViolations(), is( 0 ) );
    }

    @Test
    public void oneMethodWithCC4() {
        results.put( MetricsResults.CC, 4 );
        statistic.inspectMethod( location, results );

        assertThat( statistic.getAverage(), is( 0.0 ) );
        assertThat( statistic.getMetricsSum(), is( 4 ) );
        assertThat( statistic.getBasis(), is( 1 ) );
        assertThat( statistic.getHotspots().size(), is( 0 ) );
        assertThat( statistic.getViolations(), is( 0 ) );
    }

    @Test
    public void oneMethodWithCC5() {
        results.put( MetricsResults.CC, 5 );
        statistic.inspectMethod( location, results );

        assertThat( statistic.getAverage(), is( 25.0 ) );
        assertThat( statistic.getMetricsSum(), is( 5 ) );
        assertThat( statistic.getBasis(), is( 1 ) );
        assertThat( statistic.getHotspots().size(), is( 1 ) );
        assertThat( statistic.getHotspots().get( 0 ).getMetricsValue(), is( 5 ) );
        assertThat( statistic.getViolations(), is( 1 ) );
    }

    @Test
    public void twoMethodsWithCC1And5() {
        results.put( MetricsResults.CC, 1 );
        statistic.inspectMethod( location, results );
        results.put( MetricsResults.CC, 5 );
        statistic.inspectMethod( location, results );

        assertThat( statistic.getAverage(), is( 12.5 ) );
        assertThat( statistic.getMetricsSum(), is( 6 ) );
        assertThat( statistic.getBasis(), is( 2 ) );
        assertThat( statistic.getHotspots().size(), is( 1 ) );
        assertThat( statistic.getHotspots().get( 0 ).getMetricsValue(), is( 5 ) );
        assertThat( statistic.getViolations(), is( 1 ) );
    }

    @Test
    public void twoMethodsWithCC5And7() {
        results.put( MetricsResults.CC, 5 );
        statistic.inspectMethod( location, results );
        results.put( MetricsResults.CC, 7 );
        statistic.inspectMethod( location, results );

        assertThat( statistic.getAverage(), is( 50.0 ) );
        assertThat( statistic.getMetricsSum(), is( 12 ) );
        assertThat( statistic.getBasis(), is( 2 ) );
        assertThat( statistic.getHotspots().size(), is( 2 ) );
        assertThat( statistic.getHotspots().get( 0 ).getMetricsValue(), is( 5 ) );
        assertThat( statistic.getHotspots().get( 1 ).getMetricsValue(), is( 7 ) );
        assertThat( statistic.getViolations(), is( 2 ) );
    }
}
