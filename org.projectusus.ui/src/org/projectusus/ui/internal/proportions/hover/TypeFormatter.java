// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.hover;

import org.eclipse.jdt.core.IType;
import org.projectusus.core.internal.proportions.sqi.ClassRawData;
import org.projectusus.core.internal.proportions.sqi.IsisMetrics;

class TypeFormatter extends JavaElementFormatter {

    private final IType typeElement;
    private final ClassRawData classRawData;

    TypeFormatter( IType typeElement, ClassRawData classRawData ) {
        this.typeElement = typeElement;
        this.classRawData = classRawData;
    }

    String format() {
        StringBuilder sb = new StringBuilder( typeElement.getElementName() );
        sb.append( " [" ); //$NON-NLS-1$
        formatMetric( IsisMetrics.KG, classRawData.getNumberOfMethods(), sb );
        sb.append( "]" ); //$NON-NLS-1$
        return sb.toString();
    }
}
