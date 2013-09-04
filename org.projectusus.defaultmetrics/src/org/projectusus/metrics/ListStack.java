package org.projectusus.metrics;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ListStack<T> {
    private Stack<List<T>> counts = new Stack<List<T>>();

    public void startNewList() {
        counts.push( new ArrayList<T>() );
    }

    public void startNewList( T startValue ) {
        List<T> list = new ArrayList<T>();
        list.add( startValue );
        counts.push( list );
    }

    public void startNewList( List<T> startList ) {
        counts.push( startList );
    }

    public List<T> getAndClearList() {
        if( counts.size() == 0 ) {
            return new ArrayList<T>();
        }
        return counts.pop();
    }

    public void appendLastListWith( T element ) {
        List<T> newList = getAndClearList();
        newList.add( element );
        startNewList( newList );
    }

}
