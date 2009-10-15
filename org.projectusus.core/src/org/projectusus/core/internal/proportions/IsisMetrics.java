// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

import static org.projectusus.core.internal.util.CoreTexts.isisMetrics_acd;
import static org.projectusus.core.internal.util.CoreTexts.isisMetrics_cc;
import static org.projectusus.core.internal.util.CoreTexts.isisMetrics_kg;
import static org.projectusus.core.internal.util.CoreTexts.isisMetrics_ml;
import static org.projectusus.core.internal.util.CoreTexts.isisMetrics_pc;
import static org.projectusus.core.internal.util.CoreTexts.isisMetrics_ta;

import org.projectusus.core.internal.util.CoreTexts;

public enum IsisMetrics {

    TA( isisMetrics_ta ), PC( isisMetrics_pc ), CC( isisMetrics_cc ), ACD( isisMetrics_acd ), KG( isisMetrics_kg ), ML( isisMetrics_ml ), CW( CoreTexts.isisMetrics_cw );

    private final String label;

    private IsisMetrics( String label ) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
