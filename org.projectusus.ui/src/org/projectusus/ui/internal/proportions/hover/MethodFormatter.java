// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.hover;

import static org.projectusus.core.internal.proportions.sqi.IsisMetrics.CC;
import static org.projectusus.core.internal.proportions.sqi.IsisMetrics.ML;

import org.eclipse.jdt.core.IMethod;
import org.projectusus.core.internal.bugreport.Bug;
import org.projectusus.core.internal.bugreport.BugList;
import org.projectusus.core.internal.proportions.sqi.ClassRawData;
import org.projectusus.core.internal.proportions.sqi.IsisMetrics;
import org.projectusus.core.internal.proportions.sqi.MethodRawData;

class MethodFormatter {

    private final MethodRawData methodResults;
    private final IMethod method;
    private final ClassRawData classResults;
    private final BugList bugs;

    MethodFormatter( IMethod method, MethodRawData methodResults, ClassRawData classResults, BugList bugs ) {
        this.method = method;
        this.methodResults = methodResults;
        this.classResults = classResults;
        this.bugs = bugs;
    }

    String format() {
        StringBuilder sb = new StringBuilder( method.getElementName() );
        sb.append( " [" ); //$NON-NLS-1$
        formatMetric( CC, methodResults.getCCResult(), sb );
        sb.append( ", " ); //$NON-NLS-1$
        formatMetric( ML, methodResults.getMLResult(), sb );
        sb.append( ", " ); //$NON-NLS-1$
        formatMetric( IsisMetrics.KG, classResults.getNumberOfMethods(), sb );
        sb.append( ", " ); //$NON-NLS-1$
        formatBugs( sb );
        sb.append( "]" ); //$NON-NLS-1$
        return sb.toString();
    }

    private void formatBugs( StringBuilder sb ) {
        for( Bug bug : bugs ) {
            sb.append( "Bug: " ); //$NON-NLS-1$
            sb.append( bug.getTitle() );
        }

    }

    private void formatMetric( IsisMetrics metric, int value, StringBuilder sb ) {
        sb.append( metric.getLabel() );
        sb.append( ":" ); //$NON-NLS-1$
        sb.append( value );
    }
}
