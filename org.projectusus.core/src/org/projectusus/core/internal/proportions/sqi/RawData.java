// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.sqi;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.projectusus.core.internal.proportions.model.IHotspot;

public class RawData<S, T extends IRawData> implements IRawData {

    private final RawDataMapWrapper<S, T> wrapper;

    public RawData() {
        wrapper = new RawDataMapWrapper<S, T>();
    }

    public T getRawData( S key, T newObject ) {
        return wrapper.getRawData( key, newObject );
    }

    protected int getRawDataElementCount() {
        return wrapper.getRawDataElementCount();
    }

    public void remove( S key ) {
        wrapper.remove( key );
    }

    public int getViolationCount( IsisMetrics metric ) {
        int violations = 0;
        for( T result : wrapper.getAllRawDataElements() ) {
            violations = violations + result.getViolationCount( metric );
        }
        return violations;
    }

    public int getViolationBasis( IsisMetrics metric ) {
        int basis = 0;
        for( T result : wrapper.getAllRawDataElements() ) {
            basis = basis + result.getViolationBasis( metric );
        }
        return basis;
    }

    public void addHotspots( IsisMetrics metric, List<IHotspot> hotspots ) {
        for( T result : wrapper.getAllRawDataElements() ) {
            result.addHotspots( metric, hotspots );
        }
    }

    public int getOverallMetric( IsisMetrics metric ) {
        int overallMetricValue = 0;
        Collection<T> allDataElements = wrapper.getAllRawDataElements();
        for( T currentResult : allDataElements ) {
            overallMetricValue += currentResult.getOverallMetric( metric );
        }
        return overallMetricValue;
    }

    protected Set<S> getAllKeys() {
        return wrapper.getAllKeys();
    }

}
