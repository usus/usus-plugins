// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.sqi;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class Results<S, T extends IResults> implements IResults {

    private final ResultMapWrapper<S, T> wrapper;

    public Results() {
        wrapper = new ResultMapWrapper<S, T>();
    }

    public T getResults( S key, T newObject ) {
        return wrapper.getResults( key, newObject );
    }

    public int getResultCount() {
        return wrapper.getResultCount();
    }

    public void remove( S key ) {
        wrapper.remove( key );
    }

    public int getViolationCount( IsisMetrics metric ) {
        int violations = 0;
        for( T result : wrapper.getAllResults() ) {
            violations = violations + result.getViolationCount( metric );
        }
        return violations;
    }

    public int getViolationBasis( IsisMetrics metric ) {
        int basis = 0;
        for( T result : wrapper.getAllResults() ) {
            basis = basis + result.getViolationBasis( metric );
        }
        return basis;
    }

    public void getViolationNames( IsisMetrics metric, List<String> violations ) {
        for( T result : wrapper.getAllResults() ) {
            result.getViolationNames( metric, violations );
        }
    }

    public void getViolationLineNumbers( IsisMetrics metric, List<Integer> violations ) {
        for( T result : wrapper.getAllResults() ) {
            result.getViolationLineNumbers( metric, violations );
        }
    }

    // TODO verstecken
    protected Set<S> keySet() {
        return wrapper.keySet();
    }

    // TODO verstecken
    protected T get( S key ) {
        return wrapper.get( key );
    }

    protected Collection<T> values() {
        return wrapper.values();
    }

}
