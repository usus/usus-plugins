package org.projectusus.core.internal.proportions.sqi;

public abstract class ViolationTest {

    public boolean isViolatedBy( MethodResults methodResult ) {
        return false;
    }

    public boolean isViolatedBy( ClassResults methodResult ) {
        return false;
    }

    public abstract boolean isMethodTest();

}
