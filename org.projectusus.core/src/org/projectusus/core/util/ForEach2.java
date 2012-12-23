package org.projectusus.core.util;

import ch.akuhn.foreach.Query;

public class ForEach2 {

    public static <T> Query<T, SelectUnique<T>> selectUnique( Iterable<T> all ) {
        return Query.with( new SelectUnique<T>(), all );
    }

    public static <T> Query<T, SelectUnique<T>> selectUnique( T[] all ) {
        return Query.with( new SelectUnique<T>(), all );
    }

}
