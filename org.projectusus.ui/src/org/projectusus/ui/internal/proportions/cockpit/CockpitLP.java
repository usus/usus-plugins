// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.cockpit;

import static org.projectusus.ui.internal.proportions.cockpit.CockpitColumnDesc.INDICATOR;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.projectusus.core.basis.CodeProportion;
import org.projectusus.ui.internal.proportions.UsusModelLabelProvider;

public class CockpitLP extends UsusModelLabelProvider implements ITableLabelProvider {

    public Image getColumnImage( Object element, int columnIndex ) {
        Image result = null;
        if( CockpitColumnDesc.values()[columnIndex].hasImage() ) {
            result = getColumnImageFor( element );
        }
        return result;
    }

    public String getColumnText( Object element, int columnIndex ) {
        String result = null;
        if( element instanceof CodeProportion ) {
            result = getColumnTextFor( (CodeProportion)element, columnIndex );
        } else if( CockpitColumnDesc.values()[columnIndex] == INDICATOR ) {
            result = getNodeTextFor( element );
        }
        return result;
    }

    // internal methods
    // ////////////////

    private String getColumnTextFor( CodeProportion element, int columnIndex ) {
        return CockpitColumnDesc.values()[columnIndex].getLabel( element );
    }
}
