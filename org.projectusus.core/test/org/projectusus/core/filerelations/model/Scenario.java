package org.projectusus.core.filerelations.model;

public class Scenario {

    private final int expectedResult;

    public Scenario( int expectedResult, ClassDescriptor... input ) {
        this.expectedResult = expectedResult;
        for( int i = 0; i < input.length; i += 2 ) {
            FileRelation.of( input[i], input[i++] );
        }
    }

    public int getExpectedResult() {
        return expectedResult;
    }
}
