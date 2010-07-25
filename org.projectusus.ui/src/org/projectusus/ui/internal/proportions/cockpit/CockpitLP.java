// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.cockpit;

import static org.projectusus.ui.internal.proportions.cockpit.CockpitColumnDesc.Indicator;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.projectusus.ui.internal.AnalysisDisplayEntry;
import org.projectusus.ui.internal.proportions.UsusModelLabelProvider;

public class CockpitLP extends UsusModelLabelProvider implements ITableLabelProvider {

    public Image getColumnImage( Object element, int columnIndex ) {
        CockpitColumnDesc cockpitColumnDesc = CockpitColumnDesc.values()[columnIndex];
        if( cockpitColumnDesc == CockpitColumnDesc.Trend && element instanceof AnalysisDisplayEntry ) {
            return ((AnalysisDisplayEntry)element).getTrendImage();
        }
        if( cockpitColumnDesc.hasImage() ) {
            return getColumnImageFor( element );
        }
        return null;
    }

    public String getColumnText( Object element, int columnIndex ) {
        if( element instanceof AnalysisDisplayEntry ) {
            return getColumnTextFor( (AnalysisDisplayEntry)element, columnIndex );
        } else if( CockpitColumnDesc.values()[columnIndex] == Indicator ) {
            return getNodeTextFor( element );
        }
        return null;
    }

    // internal methods
    // ////////////////

    private String getColumnTextFor( AnalysisDisplayEntry element, int columnIndex ) {
        return CockpitColumnDesc.values()[columnIndex].getLabel( element );
    }

}
