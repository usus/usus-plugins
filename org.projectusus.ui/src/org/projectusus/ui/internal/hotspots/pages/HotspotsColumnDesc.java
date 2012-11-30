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

public enum HotspotsColumnDesc implements IColumnDesc<DisplayHotspot<?>> {

    @UsusTreeColumn( align = RIGHT, weight = 5, numeric = true )
    Value( "Value" ) { //$NON-NLS-1$
        @Override
        public String getLabel( DisplayHotspot<?> element ) {
            return String.valueOf( element.getMetricsValue() );
        }
    },
    @UsusTreeColumn( weight = 25 )
    Name( "Name" ) { //$NON-NLS-1$
        @Override
        public String getLabel( DisplayHotspot<?> element ) {
            return element.getName();
        }
    },
    @UsusTreeColumn( weight = 60 )
    Path( "Path" ) { //$NON-NLS-1$
        @Override
        public String getLabel( DisplayHotspot<?> element ) {
            return element.getPath();
        }

        @Override
        public String getHeader() {
            // TODO Hier weitermachen
            return super.getHeader();
        }
    },
    @UsusTreeColumn( align = CENTER, weight = 8, numeric = true )
    Trend( "Trend" ) { //$NON-NLS-1$
        @Override
        public boolean hasImage() {
            return true;
        }

        @Override
        public String getLabel( DisplayHotspot<?> element ) {
            return String.valueOf( element.getTrend() );
        }
    };

    private final String header;

    HotspotsColumnDesc( String header ) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }

    public boolean hasImage() {
        return false;
    }

    public String getLabel( @SuppressWarnings( "unused" ) DisplayHotspot<?> element ) {
        return ""; //$NON-NLS-1$
    }

}
