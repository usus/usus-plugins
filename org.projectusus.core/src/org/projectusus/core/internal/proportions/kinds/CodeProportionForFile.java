package org.projectusus.core.internal.proportions.kinds;

import org.projectusus.core.internal.proportions.sqi.CodeProportionUnit;



public abstract class CodeProportionForFile extends CodeProportionKind {

    public CodeProportionForFile( String label ) {
        super( label );
    }

    @Override
    public CodeProportionUnit getUnit() {
        return CodeProportionUnit.FILE;
    }

}
