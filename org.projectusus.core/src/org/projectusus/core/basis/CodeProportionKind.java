// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.basis;

import static org.projectusus.core.internal.util.CoreTexts.isisMetrics_acd;
import static org.projectusus.core.internal.util.CoreTexts.isisMetrics_cc;
import static org.projectusus.core.internal.util.CoreTexts.isisMetrics_kg;
import static org.projectusus.core.internal.util.CoreTexts.isisMetrics_ml;
import static org.projectusus.core.internal.util.CoreTexts.isisMetrics_pc;

public enum CodeProportionKind {

    PC( isisMetrics_pc, CodeProportionUnit.PACKAGE ), //
    ACD( isisMetrics_acd, CodeProportionUnit.CLASS ), //
    CC( isisMetrics_cc, CodeProportionUnit.METHOD ), //
    KG( isisMetrics_kg, CodeProportionUnit.CLASS ), //
    ML( isisMetrics_ml, CodeProportionUnit.METHOD );

    private final String label;
    private final CodeProportionUnit unit;

    private CodeProportionKind( String label, CodeProportionUnit unit ) {
        this.label = label;
        this.unit = unit;
    }

    public String getLabel() {
        return label;
    }

    public final CodeProportionUnit getUnit() {
        return unit;
    }
}
