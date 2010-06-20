// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.hotspots.pages;

import static org.projectusus.ui.viewer.ColumnAlignment.RIGHT;

import org.projectusus.core.basis.IHotspot;
import org.projectusus.ui.viewer.UsusTreeColumn;

public enum HotspotsColumnDesc implements IHotspotsPageColumnDesc {

    @UsusTreeColumn( header = "Value", align = RIGHT, weight = 5 )
    Value {
        public String getLabel( IHotspot element ) {
            return String.valueOf( element.getMetricsValue() );
        }
    },
    @UsusTreeColumn( header = "Name", weight = 25 )
    Name {
        public String getLabel( IHotspot element ) {
            return element.getName();
        }
    },
    @UsusTreeColumn( header = "Path", weight = 20 )
    Path {
        public String getLabel( IHotspot element ) {
            return element.getFile().getFullPath().removeLastSegments( 1 ).toOSString();
        }
    };

    public boolean hasImage() {
        return false;
    }
}
