// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.hotspots.pages;

import static org.projectusus.ui.viewer.ColumnAlignment.RIGHT;

import org.projectusus.core.basis.IHotspot;
import org.projectusus.ui.viewer.UsusTreeColumn;

public enum CCColumnDesc implements IHotspotsPageColumnDesc {

    @UsusTreeColumn( header = "Cyclomatic complexity", align = RIGHT, weight = 10 )
    COMPLEXITY {
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
    PATH {
        public String getLabel( IHotspot element ) {
            return element.getFile().getFullPath().removeLastSegments( 1 ).toOSString();
        }
    },
    @UsusTreeColumn( header = "Line", align = RIGHT, weight = 10 )
    LINE {
        public String getLabel( IHotspot element ) {
            return String.valueOf( element.getLineNumber() );
        }
    };

    public boolean hasImage() {
        return false;
    }
}
