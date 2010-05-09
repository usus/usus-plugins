// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.cockpit;

import static org.projectusus.ui.internal.viewer.ColumnAlignment.RIGHT;

import java.text.DecimalFormat;

import org.projectusus.core.basis.CodeProportion;
import org.projectusus.ui.internal.viewer.IColumnDesc;
import org.projectusus.ui.internal.viewer.UsusTreeColumn;

enum CockpitColumnDesc implements IColumnDesc<CodeProportion> {

    @UsusTreeColumn( header = "Indicator", weight = 56 )
    INDICATOR( true ) {
        public String getLabel( CodeProportion element ) {
            return element.getMetric().getLabel();
        }
    },
    @UsusTreeColumn( header = "Level", align = RIGHT, weight = 10 )
    SQI( false ) {
        public String getLabel( CodeProportion element ) {
            DecimalFormat formatter = new DecimalFormat( "#.##" );
            return String.valueOf( formatter.format( element.getLevel() ) );
        }
    },
    @UsusTreeColumn( header = "Violations", align = RIGHT, weight = 14 )
    VIOLATIONS( false ) {
        public String getLabel( CodeProportion element ) {
            return String.valueOf( element.getViolations() );
        }
    },
    @UsusTreeColumn( header = "Total", align = RIGHT, weight = 20 )
    CASES( false ) {
        public String getLabel( CodeProportion element ) {
            return String.valueOf( element.getBasis() );
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
