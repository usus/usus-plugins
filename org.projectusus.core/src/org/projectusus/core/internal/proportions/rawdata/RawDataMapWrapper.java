// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

class RawDataMapWrapper<Resource, RawDataContainer> {

    private final Map<Resource, RawDataContainer> rawDataMap = new Hashtable<Resource, RawDataContainer>();

    RawDataContainer getRawData( Resource name ) {
        return rawDataMap.get( name );
    }

    void addRawData( Resource key, RawDataContainer newObject ) {
        rawDataMap.put( key, newObject );
    }

    int getRawDataElementCount() {
        return rawDataMap.size();
    }

    Collection<RawDataContainer> getAllRawDataElements() {
        return rawDataMap.values();
    }

    void remove( Resource key ) {
        rawDataMap.remove( key );
    }

    Set<Resource> getAllKeys() {
        return rawDataMap.keySet();
    }

    public void clear() {
        rawDataMap.clear();
    }
}
