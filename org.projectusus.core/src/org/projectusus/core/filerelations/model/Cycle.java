package org.projectusus.core.filerelations.model;

import net.sourceforge.c4j.*;

import java.util.Set;

@ContractReference(contractClassName = "CycleContract")
public class Cycle<Element> {

    private final Set<Element> elementsInCycle;

    public Cycle( Set<Element> elements ) {
        if( elements == null ) {
            throw new IllegalArgumentException( "Null sets not allowed." ); //$NON-NLS-1$
        }
        if( elements.size() < 2 ) {
            throw new IllegalArgumentException( "A cycle needs at least 2 elements." ); //$NON-NLS-1$
        }
        this.elementsInCycle = elements;
    }

    public int numberOfElements() {
        return elementsInCycle.size();
    }

    public boolean contains( Element packagename ) {
        return elementsInCycle.contains( packagename );
    }

    public Set<Element> getElementsInCycle() {
        return elementsInCycle;
    }

}
