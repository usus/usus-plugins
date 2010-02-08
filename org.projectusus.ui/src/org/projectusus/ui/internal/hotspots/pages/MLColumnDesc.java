// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.hotspots.pages;

import static org.projectusus.ui.internal.viewer.ColumnAlignment.RIGHT;

import org.projectusus.core.internal.proportions.model.IHotspot;
import org.projectusus.core.internal.proportions.model.IMetricMLHotspot;
import org.projectusus.ui.internal.viewer.UsusTreeColumn;

public enum MLColumnDesc implements IHotspotsPageColumnDesc {

    @UsusTreeColumn( header = "Length", align = RIGHT, weight = 10 )
    LENGTH {
        public String getLabel( IHotspot element ) {
            IMetricMLHotspot hotspot = (IMetricMLHotspot)element;
            return String.valueOf( hotspot.getMethodLength() );
        }
    },
    @UsusTreeColumn( header = "Method", weight = 35 )
    METHOD {
        public String getLabel( IHotspot element ) {
            return ((IMetricMLHotspot)element).getMethodName();
        }
    },
    @UsusTreeColumn( header = "Class", weight = 25 )
    CLASS {
        public String getLabel( IHotspot element ) {
            return ((IMetricMLHotspot)element).getClassName();
        }
    },
    @UsusTreeColumn( header = "Path", weight = 20 )
    PATH {
        public String getLabel( IHotspot element ) {
            return ((IMetricMLHotspot)element).getFile().getFullPath().removeLastSegments( 1 ).toOSString();
        }
    },
    @UsusTreeColumn( header = "Line", align = RIGHT, weight = 10 )
    LINE {
        public String getLabel( IHotspot element ) {
            return String.valueOf( ((IMetricMLHotspot)element).getLineNumber() );
        }
    };

    public boolean hasImage() {
        return false;
    }
}
