// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.hover;

import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.CC;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.ML;

import org.eclipse.jdt.core.IMethod;
import org.projectusus.core.internal.bugreport.BugList;
import org.projectusus.core.internal.proportions.rawdata.CodeProportionKind;
import org.projectusus.core.internal.proportions.rawdata.IClassRawData;
import org.projectusus.core.internal.proportions.rawdata.IMethodRawData;

class MethodFormatter extends JavaElementFormatter {

    private final IMethodRawData methodRawData;
    private final IMethod method;
    private final IClassRawData classRawData;
    private final BugList bugs;

    MethodFormatter( IMethod method, IMethodRawData methodRawData, IClassRawData classRawData, BugList bugs ) {
        this.method = method;
        this.methodRawData = methodRawData;
        this.classRawData = classRawData;
        this.bugs = bugs;
    }

    String format() {
        StringBuilder sb = new StringBuilder( method.getElementName() );
        sb.append( " [" ); 
        formatMetric( CC, methodRawData.getCCValue(), sb );
        sb.append( ", " ); 
        formatMetric( ML, methodRawData.getMLValue(), sb );
        sb.append( ", " ); 
        formatMetric( CodeProportionKind.KG, classRawData.getNumberOfMethods(), sb );
        sb.append( ", " ); 
        formatBugs( sb, bugs );
        sb.append( "]" ); 
        return sb.toString();
    }

}
