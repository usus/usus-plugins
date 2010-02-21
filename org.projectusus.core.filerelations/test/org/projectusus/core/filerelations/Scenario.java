package org.projectusus.core.filerelations;

import static org.projectusus.core.filerelations.TestServiceManager.asSet;

import java.util.Set;

public class Scenario {

    private final int expectedResult;
    private final Set<FileRelation> input;

    public Scenario( int expectedResult, FileRelation... input ) {
        this( expectedResult, asSet( input ) );
    }

    public Scenario( int expectedResult, Set<FileRelation> input ) {
        this.expectedResult = expectedResult;
        this.input = input;
    }

    public int getExpectedResult() {
        return expectedResult;
    }

    public Set<FileRelation> getInput() {
        return input;
    }

}
