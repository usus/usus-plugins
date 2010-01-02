package org.projectusus.core.internal.proportions.rawdata;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class AllKnownClasses {

    private final Set<ClassRawData> allKnownClasses;
    private boolean knownClassesAreUpToDate;

    public AllKnownClasses() {
        knownClassesAreUpToDate = false;
        allKnownClasses = new TreeSet<ClassRawData>( new Comparator<ClassRawData>() {
            public int compare( ClassRawData o1, ClassRawData o2 ) {
                if( o1 == o2 ) {
                    return 0;
                }
                return 1;
            }
        } );
    }

    public void invalidate() {
        knownClassesAreUpToDate = false;
    }

    public Set<ClassRawData> getClasses() {
        return Collections.unmodifiableSet( allKnownClasses );
    }

    public boolean areUpToDate() {
        return knownClassesAreUpToDate;
    }

    public void startInitialization( ClassRawData data ) {
        knownClassesAreUpToDate = true; // here already, to avoid infinite loops
        allKnownClasses.clear();
        allKnownClasses.add( data );
    }

    public void addKnownClassesOf( ClassRawData data ) {
        if( !allKnownClasses.contains( data ) ) {
            allKnownClasses.addAll( data.getAllKnownClasses() );
        }
    }

    public int size() {
        return allKnownClasses.size();
    }
}
