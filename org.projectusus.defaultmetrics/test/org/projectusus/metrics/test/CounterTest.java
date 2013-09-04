package org.projectusus.metrics.test;

import static org.projectusus.core.testutil.PrimitiveAssert.assertThat;
import static org.projectusus.core.testutil.PrimitiveMatchers.is;

import org.junit.Test;
import org.projectusus.metrics.Counter;

public class CounterTest {
    private Counter counter = new Counter();

    @Test
    public void getAndClearCount_yields0() {
        assertThat( counter.getAndClearCount(), is( 0 ) );
    }

    @Test
    public void startNewCount_yields0() {
        counter.startNewCount();

        assertThat( counter.getAndClearCount(), is( 0 ) );
    }

    @Test
    public void startNewCount5_yields5() {
        counter.startNewCount( 5 );

        assertThat( counter.getAndClearCount(), is( 5 ) );
        assertThat( counter.getAndClearCount(), is( 0 ) );
    }

    @Test
    public void startNewCount5_increase1_yields6() {
        counter.startNewCount( 5 );
        counter.increaseLastCountBy( 1 );

        assertThat( counter.getAndClearCount(), is( 6 ) );
        assertThat( counter.getAndClearCount(), is( 0 ) );
    }

    @Test
    public void startNewCount3_4_5_yields5_4_3() {
        counter.startNewCount( 3 );
        counter.startNewCount( 4 );
        counter.startNewCount( 5 );

        assertThat( counter.getAndClearCount(), is( 5 ) );
        assertThat( counter.getAndClearCount(), is( 4 ) );
        assertThat( counter.getAndClearCount(), is( 3 ) );
        assertThat( counter.getAndClearCount(), is( 0 ) );
    }

    @Test
    public void startNewCount1_3_5_increase1_yields6_4_2() {
        counter.startNewCount( 1 );
        counter.increaseLastCountBy( 1 );
        counter.startNewCount( 3 );
        counter.increaseLastCountBy( 1 );
        counter.startNewCount( 5 );
        counter.increaseLastCountBy( 1 );

        assertThat( counter.getAndClearCount(), is( 6 ) );
        assertThat( counter.getAndClearCount(), is( 4 ) );
        assertThat( counter.getAndClearCount(), is( 2 ) );
        assertThat( counter.getAndClearCount(), is( 0 ) );
    }
}
