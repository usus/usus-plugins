// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.sqi;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

public class ResultMapWrapper<S, T extends IResults> {

    private final Map<S, T> resultMap = new Hashtable<S, T>();

    protected T getResults( S name, T newObject ) {
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

    protected void remove( S key ) {
        resultMap.remove( key );
    }

    public Set<S> getAllKeys() {
        return resultMap.keySet();
    }
}
