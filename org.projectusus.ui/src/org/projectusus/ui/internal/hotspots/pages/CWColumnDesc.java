// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.hotspots.pages;

import static org.projectusus.ui.internal.viewer.ColumnAlignment.RIGHT;

import org.projectusus.core.internal.proportions.model.IHotspot;
import org.projectusus.core.internal.proportions.model.IMetricCWHotspot;
import org.projectusus.ui.internal.viewer.UsusTreeColumn;

public enum CWColumnDesc implements IHotspotsPageColumnDesc {
    @UsusTreeColumn( header = "Warnings", align = RIGHT, weight = 10 )
    WARNINGS {
        public String getLabel( IHotspot element ) {
            IMetricCWHotspot hotspot = (IMetricCWHotspot)element;
            return String.valueOf( hotspot.getWarningCount() );
        }
    },
    @UsusTreeColumn( header = "Class", weight = 25 )
    CLASS {
        public String getLabel( IHotspot element ) {
            return ((IMetricCWHotspot)element).getFileName();
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
