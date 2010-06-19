package org.projectusus.core.internal.proportions.rawdata;

import static org.junit.Assert.assertEquals;
import static org.projectusus.core.basis.CodeProportionKind.ACD;
import static org.projectusus.core.basis.CodeProportionKind.CC;
import static org.projectusus.core.basis.CodeProportionKind.KG;
import static org.projectusus.core.basis.CodeProportionKind.ML;
import static org.projectusus.core.basis.CodeProportionKind.PC;

import org.junit.Test;
import org.projectusus.core.basis.CodeProportionUnit;

public class CodeProportionsKindTest {

    @Test
    public void testGetLabel() {
        assertEquals( "Average component dependency", ACD.getLabel() ); //$NON-NLS-1$
        assertEquals( "Cyclomatic complexity", CC.getLabel() ); //$NON-NLS-1$
        assertEquals( "Class size", KG.getLabel() ); //$NON-NLS-1$
        assertEquals( "Method length", ML.getLabel() ); //$NON-NLS-1$
        assertEquals( "Packages in cycles", PC.getLabel() ); //$NON-NLS-1$
    }

    @Test
    public void testGetUnit() {
        assertEquals( CodeProportionUnit.CLASS, ACD.getUnit() );
        assertEquals( CodeProportionUnit.METHOD, CC.getUnit() );
        assertEquals( CodeProportionUnit.CLASS, KG.getUnit() );
        assertEquals( CodeProportionUnit.METHOD, ML.getUnit() );
        assertEquals( CodeProportionUnit.PACKAGE, PC.getUnit() );
    }
}
