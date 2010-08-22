package org.projectusus.ui.internal.proportions.cockpit;

import static org.junit.Assert.assertEquals;
import static org.projectusus.ui.internal.proportions.cockpit.CockpitColumnDesc.Average;

import org.junit.Test;
import org.projectusus.core.basis.CodeProportion;
import org.projectusus.ui.internal.AnalysisDisplayEntry;

public class CockpitColumnDescTest {

    @Test
    public void averageFormat() {
        assertEquals( "0,0", getLabel( 0 ) ); //$NON-NLS-1$
        assertEquals( "0,9", getLabel( 0.9 ) ); //$NON-NLS-1$
        assertEquals( "100,0", getLabel( 100 ) ); //$NON-NLS-1$
    }

    private String getLabel( double average ) {
        return Average.getLabel( new AnalysisDisplayEntry( new CodeProportion( null, null, 0, null, average ) ) );
    }
}
