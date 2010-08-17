// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.cockpit;

import static org.projectusus.ui.viewer.ColumnAlignment.CENTER;
import static org.projectusus.ui.viewer.ColumnAlignment.RIGHT;

import java.text.DecimalFormat;

import org.projectusus.ui.internal.AnalysisDisplayEntry;
import org.projectusus.ui.viewer.IColumnDesc;
import org.projectusus.ui.viewer.UsusTreeColumn;

enum CockpitColumnDesc implements IColumnDesc<AnalysisDisplayEntry> {

    @UsusTreeColumn( header = "Indicator", weight = 50 )
    Indicator( true ) {
        public String getLabel( AnalysisDisplayEntry element ) {
            return element.getLabel();
        }
    },
    @UsusTreeColumn( header = "Level", align = RIGHT, weight = 8, numeric = true )
    Level( false ) {
        public String getLabel( AnalysisDisplayEntry element ) {
            return formatter.format( element.getLevel() );
        }
    },
    @UsusTreeColumn( header = "Hotspots", align = RIGHT, weight = 14, numeric = true )
    Violations( false ) {
        public String getLabel( AnalysisDisplayEntry element ) {
            return String.valueOf( element.getViolations() );
        }
    },
    @UsusTreeColumn( header = "Total", align = RIGHT, weight = 20 )
    Total( false ) {
        public String getLabel( AnalysisDisplayEntry element ) {
            return element.getBasis();
        }
    },
    @UsusTreeColumn( header = "Trend", align = CENTER, weight = 8, sortable = false )
    Trend( false ) {
        public String getLabel( @SuppressWarnings( "unused" ) AnalysisDisplayEntry element ) {
            return ""; // using image //$NON-NLS-1$
        }
    };

    private final boolean hasImage;
    private final static DecimalFormat formatter = new DecimalFormat( "#.##" ); //$NON-NLS-1$

    CockpitColumnDesc( boolean hasImage ) {
        this.hasImage = hasImage;
    }

    public boolean hasImage() {
        return hasImage;
    }
}
