// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.hotspots.pages;

import static org.projectusus.ui.viewer.ColumnAlignment.CENTER;
import static org.projectusus.ui.viewer.ColumnAlignment.RIGHT;

import org.projectusus.ui.internal.DisplayHotspot;
import org.projectusus.ui.viewer.IColumnDesc;
import org.projectusus.ui.viewer.UsusTreeColumn;

public enum HotspotsColumnDesc implements IColumnDesc<DisplayHotspot> {

    @UsusTreeColumn( header = "Value", align = RIGHT, weight = 5 )
    Value {
        @Override
        public String getLabel( DisplayHotspot element ) {
            return String.valueOf( element.getMetricsValue() );
        }
    },
    @UsusTreeColumn( header = "Name", weight = 25 )
    Name {
        @Override
        public String getLabel( DisplayHotspot element ) {
            return element.getName();
        }
    },
    @UsusTreeColumn( header = "Path", weight = 20 )
    Path {
        @Override
        public String getLabel( DisplayHotspot element ) {
            return element.getFile().getFullPath().removeLastSegments( 1 ).toOSString();
        }
    },
    @UsusTreeColumn( header = "Trend", align = CENTER, weight = 8 )
    Trend() {
        @Override
        public boolean hasImage() {
            return true;
        }
    };

    public boolean hasImage() {
        return false;
    }

    public String getLabel( DisplayHotspot element ) {
        return "";
    }

}
