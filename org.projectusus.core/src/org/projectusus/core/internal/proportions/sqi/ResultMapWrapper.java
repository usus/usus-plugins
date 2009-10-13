package org.projectusus.core.internal.proportions.sqi;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class ResultMapWrapper<T> {

    private final Map<String, T> resultMap = new HashMap<String, T>();

    protected T getResults( String name, T newObject ) {
        T results = resultMap.get( name );
        if( results == null ) {
            results = newObject;
            resultMap.put( name, results );
        }
        return results;
    }

    protected int getResultCount() {
        return resultMap.size();
    }

    protected Collection<T> getAllResults() {
        return resultMap.values();
    }

}
