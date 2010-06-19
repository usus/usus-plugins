// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import java.util.Collection;
import java.util.Set;

import org.projectusus.core.basis.IRawData;

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

    synchronized void removeAll() {
        wrapper.removeAll();
    }

    synchronized Set<S> getAllKeys() {
        return wrapper.getAllKeys();
    }

    synchronized Collection<T> getAllRawDataElements() {
        return wrapper.getAllRawDataElements();
    }
}
