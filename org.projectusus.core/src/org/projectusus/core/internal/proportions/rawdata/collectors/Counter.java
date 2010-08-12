package org.projectusus.core.internal.proportions.rawdata.collectors;

import java.util.Stack;

public class Counter {
    private Stack<Integer> counts = new Stack<Integer>();
    private String currentThread = "";
    {
        currentThread = getCurrentThreadName();
    }

    public void startNewCount() {
        startNewCount( 0 );
    }

    public void startNewCount( int startValue ) {
        checkCurrentThread();
        counts.push( new Integer( startValue ) );
    }

    public int getAndClearCount() {
        checkCurrentThread();
        if( counts.size() == 0 ) {
            System.out.println( "empty stack" );
            return 0;
        }
        Integer value = counts.pop();
        return value.intValue();
    }

    public void increaseLastCountBy( int amount ) {
        int newValue = amount + getAndClearCount();
        startNewCount( newValue );
    }

    private void checkCurrentThread() {
        if( !currentThread.equals( getCurrentThreadName() ) ) {
            System.out.println( "Threadname geändert" );
        }
    }

    private String getCurrentThreadName() {
        return Thread.currentThread().toString();
    }

}
