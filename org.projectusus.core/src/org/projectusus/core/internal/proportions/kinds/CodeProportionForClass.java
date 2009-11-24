package org.projectusus.core.internal.proportions.kinds;

import org.projectusus.core.internal.proportions.sqi.CodeProportionUnit;


public abstract class CodeProportionForClass extends CodeProportionKind {

    public CodeProportionForClass( String label ) {
        super( label );
    }

    @Override
    public CodeProportionUnit getUnit() {
        return CodeProportionUnit.CLASS;
    }

}
