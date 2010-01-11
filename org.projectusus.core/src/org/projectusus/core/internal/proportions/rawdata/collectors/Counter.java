package org.projectusus.core.internal.proportions.rawdata.collectors;

import java.util.Stack;

public class Counter {
    private Stack<Integer> counts = new Stack<Integer>();

    public void initCount() {
        counts.push( new Integer( 0 ) );
    }

    public int getAndClearCount() {
        Integer value = counts.pop();
        return value.intValue();
    }

    public void increaseLastCountBy( int amount ) {
        int newValue = amount + getAndClearCount();
        counts.push( new Integer( newValue ) );
    }
}
