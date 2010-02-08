// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.hotspots.pages;

import static org.projectusus.ui.internal.viewer.ColumnAlignment.RIGHT;

import org.projectusus.core.internal.proportions.model.IHotspot;
import org.projectusus.core.internal.proportions.model.IMetricKGHotspot;
import org.projectusus.ui.internal.viewer.UsusTreeColumn;

public enum KGColumnDesc implements IHotspotsPageColumnDesc {
    @UsusTreeColumn( header = "Size", align = RIGHT, weight = 10 )
    SIZE {
        public String getLabel( IHotspot element ) {
            IMetricKGHotspot hotspot = (IMetricKGHotspot)element;
            return String.valueOf( hotspot.getClassSize() );
        }
    },
    @UsusTreeColumn( header = "Class", weight = 25 )
    CLASS {
        public String getLabel( IHotspot element ) {
            return ((IMetricKGHotspot)element).getClassName();
        }
    },
    @UsusTreeColumn( header = "Path", weight = 20 )
    PATH {
        public String getLabel( IHotspot element ) {
            return element.getFile().getFullPath().removeLastSegments( 1 ).toOSString();
        }
    };

    public boolean hasImage() {
        return false;
    }
}
