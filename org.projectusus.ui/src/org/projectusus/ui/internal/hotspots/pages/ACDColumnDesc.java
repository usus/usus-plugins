// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.hotspots.pages;

import static org.projectusus.ui.internal.viewer.ColumnAlignment.LEFT;
import static org.projectusus.ui.internal.viewer.ColumnAlignment.RIGHT;

import org.projectusus.core.internal.proportions.model.IHotspot;
import org.projectusus.core.internal.proportions.model.IMetricACDHotspot;
import org.projectusus.ui.internal.viewer.ColumnAlignment;

public enum ACDColumnDesc implements IHotspotsPageColumnDesc {
    SIZE( "CCD", RIGHT, 10, false ) {
        public String getLabel( IHotspot element ) {
            IMetricACDHotspot hotspot = (IMetricACDHotspot)element;
            return String.valueOf( hotspot.getClassCCD() );
        }
    },
    CLASS( "Class", 25, false ) {
        public String getLabel( IHotspot element ) {
            return ((IMetricACDHotspot)element).getClassName();
        }
    },
    PATH( "Path", 20, false ) {
        public String getLabel( IHotspot element ) {
            return element.getFile().getFullPath().removeLastSegments( 1 ).toOSString();
        }
    };

    private final String headLabel;
    private final int weight;
    private final boolean hasImage;
    private final ColumnAlignment align;

    private ACDColumnDesc( String headLabel, ColumnAlignment align, int weight, boolean hasImage ) {
        this.headLabel = headLabel;
        this.align = align;
        this.weight = weight;
        this.hasImage = hasImage;
    }

    private ACDColumnDesc( String headLabel, int weight, boolean hasImage ) {
        this( headLabel, LEFT, weight, hasImage );
    }

    public int getWeight() {
        return weight;
    }

    public String getHeadLabel() {
        return headLabel;
    }

    public boolean hasImage() {
        return hasImage;
    }

    public ColumnAlignment getColumnAlignment() {
        return align;
    }
}
