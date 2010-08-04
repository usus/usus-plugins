// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.hotspots.pages;

import java.util.List;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.projectusus.ui.internal.DisplayHotspot;
import org.projectusus.ui.internal.proportions.UsusModelLabelProvider;

public class HotspotsLP extends UsusModelLabelProvider implements ITableLabelProvider {

    private final List<? extends HotspotsColumnDesc> columnDescs;

    public HotspotsLP( List<? extends HotspotsColumnDesc> columnDescs ) {
        this.columnDescs = columnDescs;
    }

    public String getColumnText( Object element, int columnIndex ) {
        if( element instanceof DisplayHotspot ) {
            return columnDescs.get( columnIndex ).getLabel( (DisplayHotspot<?>)element );
        }
        return element.toString();
    }

    public Image getColumnImage( Object element, int columnIndex ) {
        HotspotsColumnDesc cockpitColumnDesc = HotspotsColumnDesc.values()[columnIndex];
        if( cockpitColumnDesc == HotspotsColumnDesc.Trend && element instanceof DisplayHotspot ) {
            return ((DisplayHotspot<?>)element).getTrendImage();
        }
        if( cockpitColumnDesc.hasImage() ) {
            return getColumnImageFor( element );
        }
        return null;
    }

}
