// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.projectusus.core.internal.proportions.model.IHotspot;

class RawData<S, T extends IRawData> implements IRawData {

    private final RawDataMapWrapper<S, T> wrapper;

    RawData() {
        wrapper = new RawDataMapWrapper<S, T>();
    }

    synchronized T getRawData( S key ) {
        return wrapper.getRawData( key );
    }

    synchronized void addRawData( S key, T newObject ) {
        wrapper.addRawData( key, newObject );
    }

    synchronized int getRawDataElementCount() {
        return wrapper.getRawDataElementCount();
    }

    synchronized void remove( S key ) {
        wrapper.remove( key );
    }

    public synchronized int getViolationCount( CodeProportionKind metric ) {
        int violations = 0;
        for( T result : wrapper.getAllRawDataElements() ) {
            violations = violations + result.getViolationCount( metric );
        }
        return violations;
    }

    public synchronized int getNumberOf( CodeProportionUnit unit ) {
        int basis = 0;
        for( T result : wrapper.getAllRawDataElements() ) {
            basis = basis + result.getNumberOf( unit );
        }
        return basis;
    }

    public synchronized void addToHotspots( CodeProportionKind metric, List<IHotspot> hotspots ) {
        for( T result : wrapper.getAllRawDataElements() ) {
            result.addToHotspots( metric, hotspots );
        }
    }

    public synchronized int getOverallMetric( CodeProportionKind metric ) {
        int overallMetricValue = 0;
        Collection<T> allDataElements = wrapper.getAllRawDataElements();
        for( T currentResult : allDataElements ) {
            overallMetricValue += currentResult.getOverallMetric( metric );
        }
        return overallMetricValue;
    }

    synchronized Set<S> getAllKeys() {
        return wrapper.getAllKeys();
    }

    public void dropAllRawData() {
        wrapper.clear();
    }

    synchronized Collection<T> getAllRawDataElements() {
        return wrapper.getAllRawDataElements();
    }
}
