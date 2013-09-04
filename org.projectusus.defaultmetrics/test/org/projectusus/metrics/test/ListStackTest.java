package org.projectusus.metrics.test;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.projectusus.metrics.ListStack;

public class ListStackTest {
    private ListStack<String> counter = new ListStack<String>();

    @Test
    public void getAndClearCount_yieldsEmptyList() {
        assertThat( counter.getAndClearList(), is( empty() ) );
    }

    @Test
    public void startNewCount_yields0() {
        counter.startNewList();

        assertThat( counter.getAndClearList(), is( empty() ) );
    }

    @Test
    public void startNewCount5_yields5() {
        counter.startNewList( "A" );

        assertThat( counter.getAndClearList(), contains( "A" ) );
        assertThat( counter.getAndClearList(), is( empty() ) );
    }

    @Test
    public void startNewCount5_increase1_yields6() {
        counter.startNewList( "A" );
        counter.appendLastListWith( "B" );

        assertThat( counter.getAndClearList(), is( contains( "A", "B" ) ) );
        assertThat( counter.getAndClearList(), is( empty() ) );
    }

    @Test
    public void startNewCount3_4_5_yields5_4_3() {
        counter.startNewList( "A" );
        counter.startNewList( "B" );
        counter.startNewList( "C" );

        assertThat( counter.getAndClearList(), contains( "C" ) );
        assertThat( counter.getAndClearList(), contains( "B" ) );
        assertThat( counter.getAndClearList(), contains( "A" ) );
        assertThat( counter.getAndClearList(), is( empty() ) );
    }

    @Test
    public void startNewCount1_3_5_increase1_yields6_4_2() {
        counter.startNewList( "A" );
        counter.appendLastListWith( "a" );
        counter.startNewList( "B" );
        counter.appendLastListWith( "b" );
        counter.startNewList( "C" );
        counter.appendLastListWith( "c" );

        assertThat( counter.getAndClearList(), contains( "C", "c" ) );
        assertThat( counter.getAndClearList(), contains( "B", "b" ) );
        assertThat( counter.getAndClearList(), contains( "A", "a" ) );
        assertThat( counter.getAndClearList(), is( empty() ) );
    }
}
