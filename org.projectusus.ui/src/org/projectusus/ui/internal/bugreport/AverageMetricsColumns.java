// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.bugreport;

import org.projectusus.core.internal.bugreport.IAverageMetrics;
import org.projectusus.ui.internal.viewer.ColumnAlignment;
import org.projectusus.ui.internal.viewer.IColumnDesc;

public enum AverageMetricsColumns implements IColumnDesc<IAverageMetrics> {
    NAME( "Name", 15, false, true ) {
        public String getLabel( IAverageMetrics element ) {
            return element.getName();
        }

        @Override
        public ColumnAlignment getColumnAlignment() {
            return ColumnAlignment.LEFT;
        }
    },
    AVG_CC( "Avg. CC", 15, false, true ) {
        public String getLabel( IAverageMetrics metrics ) {
            return String.valueOf( metrics.getAverageCyclomaticComplexity() );
        }

    },
    AVG_ML( "Avg. ML", 40, false, true ) {
        public String getLabel( IAverageMetrics metrics ) {
            return String.valueOf( metrics.getAverageMethodLength() );
        }
    },
    AVG_NUMBER_OF_METHOS_IN_CLASS( "Avg. methods in class", 20, false, true ) {
        public String getLabel( IAverageMetrics metrics ) {
            return String.valueOf( metrics.getAverageNumberOfMethodsInClass() );
        }
    };

    private final String headLabel;
    private final int weight;
    private final boolean hasImage;
    private final boolean hasText;

    AverageMetricsColumns( String headLabel, int weight ) {
        this( headLabel, weight, false, false );
    }

    AverageMetricsColumns( String headLabel, int weight, boolean hasImage, boolean hasText ) {
        this.headLabel = headLabel;
        this.weight = weight;
        this.hasImage = hasImage;
        this.hasText = hasText;
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

    public ColumnAlignment getColumnAlignment() {
        return ColumnAlignment.RIGHT;
    }

    public boolean isHasText() {
        return hasText;
    }
}
