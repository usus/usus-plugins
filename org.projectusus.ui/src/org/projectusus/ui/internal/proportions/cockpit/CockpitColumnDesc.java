// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.cockpit;

import java.text.DecimalFormat;

import org.projectusus.core.internal.proportions.model.CodeProportion;
import org.projectusus.ui.internal.viewer.IColumnDesc;

enum CockpitColumnDesc implements IColumnDesc<CodeProportion> {

    INDICATOR( "Indicator", 56, true ) {
        public String getLabel( CodeProportion element ) {
            return element.getMetric().getLabel();
        }
    },
    SQI( "SQI", 10, false ) {
        public String getLabel( CodeProportion element ) {
            DecimalFormat formatter = new DecimalFormat( "#.##" ); 
            return String.valueOf( formatter.format( element.getSQIValue() ) );
        }
    },
    VIOLATIONS( "Violations", 14, false ) {
        public String getLabel( CodeProportion element ) {
            return String.valueOf( element.getViolations() );
        }
    },
    CASES( "Total", 20, false ) {
        public String getLabel( CodeProportion element ) {
            return String.valueOf( element.getBasis() ) + " " + element.getMetric().getUnit().getLabel(); 
        }
    };

    private final String headLabel;
    private final int weight;
    private final boolean hasImage;

    CockpitColumnDesc( String headLabel, int weight, boolean hasImage ) {
        this.headLabel = headLabel;
        this.weight = weight;
        this.hasImage = hasImage;
    }

    public int getWeight() {
        return weight;
    }

    public String getHeadLabel() {
        return headLabel;
    }

    public boolean hasImage() {
        return hasImage;
    }
}
