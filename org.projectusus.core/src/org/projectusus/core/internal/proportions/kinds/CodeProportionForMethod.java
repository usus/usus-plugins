package org.projectusus.core.internal.proportions.kinds;

import org.projectusus.core.internal.proportions.sqi.CodeProportionUnit;


public abstract class CodeProportionForMethod extends CodeProportionKind {

    public CodeProportionForMethod( String label ) {
        super( label );
    }

    @Override
    public CodeProportionUnit getUnit() {
        return CodeProportionUnit.METHOD;
    }

    @Override
    public boolean isMethodTest() {
        return true;
    }
}
