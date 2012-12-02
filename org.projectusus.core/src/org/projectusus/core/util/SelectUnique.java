package org.projectusus.core.util;


import java.util.HashSet;
import java.util.Set;


import ch.akuhn.foreach.For;

public class SelectUnique<Each> extends For<Each> {

    public Each value;
    public boolean yield;

    private Set<Each> result;

    @Override
    protected void afterEach() {
        if( yield ) {
            result.add( value );
        }
    }

    @Override
    protected void beforeEach( Each each ) {
        value = each;
        yield = false;
    }

    @Override
    protected void beforeLoop() {
        result = new HashSet<Each>();
    }

    @Override
    protected Set<Each> afterLoop() {
        return result;
    }

}
