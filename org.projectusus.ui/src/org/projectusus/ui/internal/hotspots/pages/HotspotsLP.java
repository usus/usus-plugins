// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.hotspots.pages;

import java.util.List;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.projectusus.core.internal.proportions.model.IHotspot;

public class HotspotsLP extends LabelProvider implements ITableLabelProvider {

    private final List<? extends IHotspotsPageColumnDesc> columnDescs;

    public HotspotsLP( List<? extends IHotspotsPageColumnDesc> columnDescs ) {
        this.columnDescs = columnDescs;
    }

    public String getColumnText( Object element, int columnIndex ) {
        String result = element.toString();
        if( element instanceof IHotspot ) {
            IHotspot hotspot = (IHotspot)element;
            result = columnDescs.get( columnIndex ).getLabel( hotspot );
        }
        return result;
    }

    public Image getColumnImage( Object element, int columnIndex ) {
        return null;
    }
}
