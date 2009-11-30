// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.hover;

import static org.projectusus.core.internal.proportions.sqi.CodeProportionKind.CC;
import static org.projectusus.core.internal.proportions.sqi.CodeProportionKind.ML;

import org.eclipse.jdt.core.IMethod;
import org.projectusus.core.internal.bugreport.BugList;
import org.projectusus.core.internal.proportions.sqi.ClassRawData;
import org.projectusus.core.internal.proportions.sqi.CodeProportionKind;
import org.projectusus.core.internal.proportions.sqi.MethodRawData;

class MethodFormatter extends JavaElementFormatter {

    private final MethodRawData methodRawData;
    private final IMethod method;
    private final ClassRawData classRawData;
    private final BugList bugs;

    MethodFormatter( IMethod method, MethodRawData methodRawData, ClassRawData classRawData, BugList bugs ) {
        this.method = method;
        this.methodRawData = methodRawData;
        this.classRawData = classRawData;
        this.bugs = bugs;
    }

    String format() {
        StringBuilder sb = new StringBuilder( method.getElementName() );
        sb.append( " [" ); //$NON-NLS-1$
        formatMetric( CC, methodRawData.getCCValue(), sb );
        sb.append( ", " ); //$NON-NLS-1$
        formatMetric( ML, methodRawData.getMLValue(), sb );
        sb.append( ", " ); //$NON-NLS-1$
        formatMetric( CodeProportionKind.KG, classRawData.getNumberOfMethods(), sb );
        sb.append( ", " ); //$NON-NLS-1$
        formatBugs( sb, bugs );
        sb.append( "]" ); //$NON-NLS-1$
        return sb.toString();
    }

}
