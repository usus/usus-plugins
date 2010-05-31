package org.projectusus.core.internal.proportions.rawdata.collectors;

import java.util.Stack;

public class Counter {
    private Stack<Integer> counts = new Stack<Integer>();

    public void startNewCount() {
        startNewCount( 0 );
    }

    public void startNewCount( int startValue ) {
        counts.push( new Integer( startValue ) );
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
