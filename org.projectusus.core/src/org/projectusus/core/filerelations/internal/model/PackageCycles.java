package org.projectusus.core.filerelations.internal.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.projectusus.core.filerelations.model.Cycle;
import org.projectusus.core.filerelations.model.Packagename;

public class PackageCycles {

    private final List<Cycle<Packagename>> packageCycles;

    public PackageCycles( List<Set<Packagename>> cycles ) {
        packageCycles = new ArrayList<Cycle<Packagename>>();
        for( Set<Packagename> cycle : cycles ) {
            if( cycle.size() > 1 ) {
                packageCycles.add( new Cycle<Packagename>( cycle ) );
            }
        }
    }

    public int numberOfPackagesInAnyCycles() {
        int count = 0;
        for( Cycle<Packagename> cycle : packageCycles ) {
            count = count + cycle.numberOfElements();
        }
        return count;
    }

    public boolean containsPackage( Packagename packagename ) {
        for( Cycle<Packagename> cycle : packageCycles ) {
            if( cycle.contains( packagename ) ) {
                return true;
            }
        }
        return false;
    }
}
