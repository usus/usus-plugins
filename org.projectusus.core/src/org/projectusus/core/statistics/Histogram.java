package org.projectusus.core.statistics;

import static java.lang.Integer.valueOf;

import java.util.SortedMap;
import java.util.TreeMap;

public class Histogram {

    private SortedMap<Integer, Integer> counters = new TreeMap<Integer, Integer>();

    public void increment( int number ) {
        int newCount = 1 + countOf( number );
        put( number, newCount );
    }

    public int countOf( int number ) {
        Integer count = get( number );
        return count == null ? 0 : count.intValue();
    }

    private void put( int number, int newCount ) {
        counters.put( valueOf( number ), valueOf( newCount ) );
    }

    private Integer get( int number ) {
        return counters.get( valueOf( number ) );
    }

    public double[] allValues() {
        int index = 0;
        double[] values = new double[counters.size()];
        for( Integer number : counters.values() ) {
            values[index++] = number.doubleValue();
        }
        return values;
    }

    public double[] allNumbers() {
        int index = 0;
        double[] values = new double[counters.size()];
        for( Integer number : counters.keySet() ) {
            values[index++] = number.doubleValue();
        }
        return values;
    }
}
