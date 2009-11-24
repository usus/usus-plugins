package org.projectusus.core.internal.proportions.kinds;

import org.projectusus.core.internal.proportions.sqi.CodeProportionUnit;


public abstract class CodeProportionForPackage extends CodeProportionKind {

    public CodeProportionForPackage( String label ) {
        super( label );
    }

    @Override
    public CodeProportionUnit getUnit() {
        return CodeProportionUnit.PACKAGE;
    }

}
