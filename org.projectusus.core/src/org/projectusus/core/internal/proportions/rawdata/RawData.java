// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import org.projectusus.core.basis.IRawData;

class RawData<S, T extends IRawData> implements IRawData {

    private Map<S, T> rawDataMap;

    RawData() {
        rawDataMap = new Hashtable<S, T>();
    }

    synchronized T getRawData( S key ) {
        return rawDataMap.get( key );
    }

    synchronized void addRawData( S key, T newObject ) {
        rawDataMap.put( key, newObject );
    }

    synchronized int getRawDataElementCount() {
        return rawDataMap.size();
    }

    synchronized void remove( S key ) {
        rawDataMap.remove( key );
    }

    synchronized void removeAll() {
        rawDataMap.clear();
    }

    synchronized Set<S> getAllKeys() {
        return rawDataMap.keySet();
    }

    synchronized Collection<T> getAllRawDataElements() {
        return rawDataMap.values();
    }
}
