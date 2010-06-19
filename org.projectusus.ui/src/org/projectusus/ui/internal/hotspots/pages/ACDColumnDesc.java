// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.hotspots.pages;

import static org.projectusus.ui.viewer.ColumnAlignment.RIGHT;

import org.projectusus.core.basis.IHotspot;
import org.projectusus.ui.viewer.UsusTreeColumn;

public enum ACDColumnDesc implements IHotspotsPageColumnDesc {
    @UsusTreeColumn( header = "CCD", align = RIGHT, weight = 10 )
    SIZE {
        public String getLabel( IHotspot element ) {
            return String.valueOf( element.getMetricsValue() );
        }
    },
    @UsusTreeColumn( header = "Class", weight = 25 )
    CLASS {
        public String getLabel( IHotspot element ) {
            return element.getName();
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
