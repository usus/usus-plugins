package org.projectusus.core.filerelations.model;

import java.util.Set;

public class PackageCycle {

    private final Set<Packagename> packagesInCycle;

    public PackageCycle( Set<Packagename> packageSet ) {
        if( packageSet == null ) {
            throw new IllegalArgumentException( "Null sets not allowed." ); //$NON-NLS-1$
        }
        if( packageSet.size() < 2 ) {
            throw new IllegalArgumentException( "A cycle needs at least 2 elements." ); //$NON-NLS-1$
        }
        this.packagesInCycle = packageSet;
    }

    public int numberOfPackages() {
        return packagesInCycle.size();
    }

}
