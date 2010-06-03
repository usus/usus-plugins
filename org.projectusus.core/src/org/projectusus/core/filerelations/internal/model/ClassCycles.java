package org.projectusus.core.filerelations.internal.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.Cycle;

public class ClassCycles {

    private final List<Cycle<ClassDescriptor>> classCycles;

    public ClassCycles( List<Set<ClassDescriptor>> cycles ) {
        classCycles = new ArrayList<Cycle<ClassDescriptor>>();
        for( Set<ClassDescriptor> cycle : cycles ) {
            if( cycle.size() > 1 ) {
                classCycles.add( new Cycle<ClassDescriptor>( cycle ) );
            }
        }
    }

    public int numberOfClassesInAnyCycles() {
        int count = 0;
        for( Cycle<ClassDescriptor> cycle : classCycles ) {
            count = count + cycle.numberOfElements();
        }
        return count;
    }

    public boolean containsClass( ClassDescriptor descriptor ) {
        for( Cycle<ClassDescriptor> cycle : classCycles ) {
            if( cycle.contains( descriptor ) ) {
                return true;
            }
        }
        return false;
    }
}
