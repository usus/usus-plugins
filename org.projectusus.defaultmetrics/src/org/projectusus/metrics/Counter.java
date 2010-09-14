package org.projectusus.metrics;

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
        if( counts.size() == 0 ) {
            return 0;
        }
        return counts.pop().intValue();
    }

    public void increaseLastCountBy( int amount ) {
        int newValue = amount + getAndClearCount();
        startNewCount( newValue );
    }

}
