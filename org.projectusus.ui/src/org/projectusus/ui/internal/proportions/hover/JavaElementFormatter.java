// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.hover;

import org.projectusus.core.internal.bugreport.Bug;
import org.projectusus.core.internal.bugreport.BugList;
import org.projectusus.core.internal.proportions.sqi.CodeProportionKind;

abstract class JavaElementFormatter {

    void formatBugs( StringBuilder sb, BugList bugs ) {
        for( Bug bug : bugs ) {
            sb.append( "Bug: " ); //$NON-NLS-1$
            sb.append( bug.getTitle() );
        }
    }

    void formatMetric( CodeProportionKind metric, int value, StringBuilder sb ) {
        sb.append( metric.getLabel() );
        sb.append( ":" ); //$NON-NLS-1$
        sb.append( value );
    }
}
