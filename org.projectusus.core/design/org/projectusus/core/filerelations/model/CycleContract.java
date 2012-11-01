package org.projectusus.core.filerelations.model;

import java.util.Set;

import org.projectusus.c4j.UsusContractBase;

public class CycleContract<Element> extends UsusContractBase<Cycle<Element>> {

    private int numberOfElements;

    private String targetString() {
        return " Target: " + m_target;
    }

    public CycleContract( Cycle<Element> target ) {
        super( target );
    }

    public void classInvariant() {
        assertThat( m_target.getElementsInCycle() != null, "Set of elements in cycle must not be null" + targetString() );
        assertThat( m_target.numberOfElements() == numberOfElements, "Cycle must not be manipulated from outside" + targetString() );
    }

    public void pre_Cycle( Set<Element> elements ) {
        // TODO Auto-generated pre-condition
        assertThat( elements != null, "elements_not_null" );
    }

    public void post_Cycle( Set<Element> elements ) {
        numberOfElements = m_target.numberOfElements();
    }

    public void pre_numberOfElements() {
        // TODO no pre-condition identified yet
    }

    public void post_numberOfElements() {
        int returnValue = ((Integer)getReturnValue()).intValue();
        // TODO no post-condition identified yet
    }

    public void pre_contains( Element packagename ) {
        // TODO Auto-generated pre-condition
        assertThat( packagename != null, "packagename_not_null" );
    }

    public void post_contains( Element packagename ) {
        boolean returnValue = ((Boolean)getReturnValue()).booleanValue();
        // TODO no post-condition identified yet
    }

    public void pre_getElementsInCycle() {
        // TODO no pre-condition identified yet
    }

    public void post_getElementsInCycle() {
        Set<Element> returnValue = (Set<Element>)getReturnValue();
        // TODO no post-condition identified yet
    }

}
