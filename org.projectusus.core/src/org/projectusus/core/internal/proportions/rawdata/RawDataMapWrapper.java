// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

class RawDataMapWrapper<S, T extends IRawData> {

    private final Map<S, T> rawDataMap = new Hashtable<S, T>();

    T getRawData( S name ) {
        return rawDataMap.get( name );
    }

    void addRawData( S key, T newObject ) {
        rawDataMap.put( key, newObject );
    }

    int getRawDataElementCount() {
        return rawDataMap.size();
    }

    Collection<T> getAllRawDataElements() {
        return rawDataMap.values();
    }

    void remove( S key ) {
        rawDataMap.remove( key );
    }

    Set<S> getAllKeys() {
        return rawDataMap.keySet();
    }
}
