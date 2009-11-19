package org.projectusus.core.internal.bugreport;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class BugListTest  {

    private BugList bugs;

    @Before
    public void setUp() {
        bugs = new BugList();
    }
    
    @Test
    public void testFilter() {
        addBug();
        BugList filteredBugs = bugs.filter( createLocation() );
        assertEquals(1, filteredBugs.size());
    }
    
    @Test
    public void testFilterWithEmptyResult() {
        addBug();
        MethodLocation location = createLocation();
        location.setClassName( "an other classname" );
        BugList filteredBugs = bugs.filter( location );
        assertTrue(filteredBugs.isEmpty());
    }

    private void addBug() {
        Bug bug = new Bug();
        bug.setLocation( createLocation() );
        bugs.addBug( bug );
    }

    private MethodLocation createLocation() {
        MethodLocation result = new MethodLocation();
        
        result.setProject( "project" );
        result.setPackageName( "packageName" );
        result.setClassName( "className" );
        result.setMethodName( "methodName" );
        
        return result;
    }

}
