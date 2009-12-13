// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.hotspots.pages;

import org.projectusus.core.internal.proportions.model.IHotspot;
import org.projectusus.core.internal.proportions.model.IMetricMLHotspot;

public enum MLColumnDesc implements IHotspotsPageColumnDesc {

    LENGTH( "Length", 10, false ) {
        public String getLabel( IHotspot element ) {
            IMetricMLHotspot hotspot = (IMetricMLHotspot)element;
            return String.valueOf( hotspot.getMethodLength() );
        }
    },
    METHOD( "Method", 35, false ) {
        public String getLabel( IHotspot element ) {
            return ((IMetricMLHotspot)element).getMethodName();
        }
    },
    CLASS( "Class", 25, false ) {
        public String getLabel( IHotspot element ) {
            return ((IMetricMLHotspot)element).getClassName();
        }
    },
    PATH( "Path", 20, false ) {
        public String getLabel( IHotspot element ) {
            return ((IMetricMLHotspot)element).getFile().getFullPath().removeLastSegments( 1 ).toOSString();
        }
    },
    LINE( "Line", 10, false ) {
        public String getLabel( IHotspot element ) {
            return String.valueOf( ((IMetricMLHotspot)element).getLineNumber() );
        }
    };

    private final String headLabel;
    private final int weight;
    private final boolean hasImage;

    private MLColumnDesc( String headLabel, int weight, boolean hasImage ) {
        this.headLabel = headLabel;
        this.weight = weight;
        this.hasImage = hasImage;
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
}
