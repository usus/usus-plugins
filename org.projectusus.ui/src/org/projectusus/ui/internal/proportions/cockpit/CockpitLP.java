// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.cockpit;

import static org.projectusus.ui.internal.proportions.cockpit.CockpitColumnDesc.Indicator;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Image;
import org.projectusus.ui.internal.AnalysisDisplayEntry;
import org.projectusus.ui.internal.IDisplayCategory;

public class CockpitLP extends ColumnLabelProvider {

    @Override
    public String getToolTipText( Object element ) {
        if( element instanceof AnalysisDisplayEntry ) {
            return ((AnalysisDisplayEntry)element).getToolTipText();
        }
        return super.getToolTipText( element );
    }

    @Override
    public int getToolTipDisplayDelayTime( @SuppressWarnings( "unused" ) Object object ) {
        return 500;
    }

    @Override
    public int getToolTipTimeDisplayed( @SuppressWarnings( "unused" ) Object object ) {
        return 10000;
    }

    @Override
    public void update( ViewerCell cell ) {
        Object element = cell.getElement();
        int columnIndex = cell.getColumnIndex();
        cell.setImage( getColumnImage( element, columnIndex ) );
        cell.setText( getColumnText( element, columnIndex ) );

    }

    private Image getColumnImage( Object element, int columnIndex ) {
        CockpitColumnDesc cockpitColumnDesc = CockpitColumnDesc.values()[columnIndex];
        if( element instanceof IDisplayCategory ) {
            return cockpitColumnDesc == Indicator ? ((IDisplayCategory)element).getImage() : null;
        }
        if( cockpitColumnDesc.hasImage() ) {
            return cockpitColumnDesc.getImage( (AnalysisDisplayEntry)element );
        }
        return null;
    }

    private String getColumnText( Object element, int columnIndex ) {
        CockpitColumnDesc cockpitColumnDesc = CockpitColumnDesc.values()[columnIndex];
        if( element instanceof AnalysisDisplayEntry ) {
            return cockpitColumnDesc.getLabel( ((AnalysisDisplayEntry)element) );
        }
        if( cockpitColumnDesc == Indicator ) {
            return ((IDisplayCategory)element).getLabel();
        }
        return null;
    }

}
