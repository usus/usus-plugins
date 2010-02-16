// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.infopresenter.infomodel;

import org.projectusus.core.internal.bugreport.Bug;
import org.projectusus.core.internal.bugreport.BugList;
import org.projectusus.core.internal.proportions.rawdata.CodeProportionKind;

class UsusModelElementFormatter {

    String format( BugList bugs ) {
        StringBuilder sb = new StringBuilder();
        for( Bug bug : bugs ) {
            formatKeyValue( "Bug", bug.getTitle(), sb );
        }
        return sb.toString();
    }

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
