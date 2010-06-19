// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.cockpit;

import static org.projectusus.ui.viewer.ColumnAlignment.CENTER;
import static org.projectusus.ui.viewer.ColumnAlignment.RIGHT;

import java.text.DecimalFormat;

import org.projectusus.core.basis.CodeProportion;
import org.projectusus.ui.viewer.IColumnDesc;
import org.projectusus.ui.viewer.UsusTreeColumn;

enum CockpitColumnDesc implements IColumnDesc<CodeProportion> {

    @UsusTreeColumn( header = "Indicator", weight = 50 )
    Indicator( true ) {
        public String getLabel( CodeProportion element ) {
            return element.getMetric().getLabel();
        }
    },
    @UsusTreeColumn( header = "Level", align = RIGHT, weight = 8 )
    Level( false ) {
        public String getLabel( CodeProportion element ) {
            DecimalFormat formatter = new DecimalFormat( "#.##" );
            return String.valueOf( formatter.format( element.getLevel() ) );
        }
    },
    @UsusTreeColumn( header = "Violations", align = RIGHT, weight = 14 )
    Violations( false ) {
        public String getLabel( CodeProportion element ) {
            return String.valueOf( element.getViolations() );
        }
    },
    @UsusTreeColumn( header = "Total", align = RIGHT, weight = 20 )
    Total( false ) {
        public String getLabel( CodeProportion element ) {
            return String.valueOf( element.getBasis() );
        }
    },
    @UsusTreeColumn( header = "Trend", align = CENTER, weight = 8 )
    Trend( false ) {
        public String getLabel( CodeProportion element ) {
            return CockpitMetricsSnapshot.trendFor( element );
        }
    };

    private final boolean hasImage;

    CockpitColumnDesc( boolean hasImage ) {
        this.hasImage = hasImage;
    }

    public boolean hasImage() {
        return hasImage;
    }
}
