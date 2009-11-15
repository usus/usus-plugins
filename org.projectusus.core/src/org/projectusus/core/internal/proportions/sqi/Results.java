// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.sqi;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.projectusus.core.internal.proportions.model.IHotspot;

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

    public void addHotspots( IsisMetrics metric, List<IHotspot> hotspots ) {
        for( T result : wrapper.getAllResults() ) {
            result.addHotspots( metric, hotspots );
        }
    }

    public int getOverallMetric( IsisMetrics metric ) {
        int overallMetricValue = 0;
        Collection<T> allResults = wrapper.getAllResults();
        for( T currentResult : allResults ) {
            overallMetricValue += currentResult.getOverallMetric( metric );
        }
        return overallMetricValue;
    }

    protected Set<S> getAllKeys() {
        return wrapper.getAllKeys();
    }

}
