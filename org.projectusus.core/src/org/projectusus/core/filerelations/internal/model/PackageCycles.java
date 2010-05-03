package org.projectusus.core.filerelations.internal.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.projectusus.core.filerelations.model.PackageCycle;
import org.projectusus.core.filerelations.model.Packagename;

public class PackageCycles {

    private final List<PackageCycle> packageCycles;

    public PackageCycles( List<Set<Packagename>> cycles ) {
        packageCycles = new ArrayList<PackageCycle>();
        for( Set<Packagename> cycle : cycles ) {
            if( cycle.size() > 1 ) {
                packageCycles.add( new PackageCycle( cycle ) );
            }
        }
    }

    public int numberOfPackagesInAnyCycles() {
        int count = 0;
        for( PackageCycle cycle : packageCycles ) {
            count = count + cycle.numberOfPackages();
        }
        return count;
    }

    public boolean containsPackage( Packagename packagename ) {
        for( PackageCycle cycle : packageCycles ) {
            if( cycle.contains( packagename ) ) {
                return true;
            }
        }
        return false;
    }
}
