package org.projectusus.core.internal.proportions.rawdata;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class SetOfClasses {

    private final Set<ClassRawData> allClasses;

    public SetOfClasses() {
        allClasses = new TreeSet<ClassRawData>( new Comparator<ClassRawData>() {
            public int compare( ClassRawData o1, ClassRawData o2 ) {
                if( o1 == o2 ) {
                    return 0;
                }
                return 1;
            }
        } );
    }

    public void invalidate() {
        allClasses.clear();
    }

    public Set<ClassRawData> getClasses() {
        return Collections.unmodifiableSet( allClasses );
    }

    public boolean areUpToDate() {
        return size() > 0;
    }

    public void startInitialization( ClassRawData data ) {
        invalidate();
        allClasses.add( data );
    }

    public void addAllClassesDependingOn( ClassRawData clazz, Collection<ClassRawData> dependingClasses ) {
        if( !allClasses.contains( clazz ) ) {
            allClasses.addAll( dependingClasses );
        }
    }

    public int size() {
        return allClasses.size();
    }
}
