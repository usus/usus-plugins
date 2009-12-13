// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.hover;

import org.eclipse.jdt.core.IType;
import org.projectusus.core.internal.proportions.rawdata.CodeProportionKind;
import org.projectusus.core.internal.proportions.rawdata.IClassRawData;

class TypeFormatter extends JavaElementFormatter {

    private final IType typeElement;
    private final IClassRawData classRawData;

    TypeFormatter( IType typeElement, IClassRawData classData ) {
        this.typeElement = typeElement;
        this.classRawData = classData;
    }

    String format() {
        StringBuilder sb = new StringBuilder( typeElement.getElementName() );
        sb.append( " [" ); 
        formatMetric( CodeProportionKind.KG, classRawData.getNumberOfMethods(), sb );
        sb.append( "]" ); 
        return sb.toString();
    }
}
