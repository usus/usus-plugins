// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.infopresenter.infomodel;

import org.projectusus.core.basis.CodeProportionKind;

class UsusModelElementFormatter {

    String format( CodeProportionKind metric, int value ) {
        StringBuilder sb = new StringBuilder();
        formatKeyValue( metric.getLabel(), String.valueOf( value ), sb );
        return sb.toString();
    }

    private void formatKeyValue( String key, String value, StringBuilder sb ) {
        sb.append( key );
        sb.append( ": " );
        sb.append( value );
    }
}
