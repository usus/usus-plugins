// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.bugreport;

import static org.projectusus.ui.internal.viewer.ColumnAlignment.RIGHT;

import org.projectusus.bugreport.core.IAverageMetrics;
import org.projectusus.ui.internal.viewer.IColumnDesc;
import org.projectusus.ui.internal.viewer.UsusTreeColumn;

public enum AverageMetricsColumns implements IColumnDesc<IAverageMetrics> {
    @UsusTreeColumn( header = "Name", weight = 15 )
    NAME( false, true ) {
        public String getLabel( IAverageMetrics element ) {
            return element.getName();
        }
    },
    @UsusTreeColumn( header = "Avg. CC", align = RIGHT, weight = 15 )
    AVG_CC( false, true ) {
        public String getLabel( IAverageMetrics metrics ) {
            return String.valueOf( metrics.getAverageCyclomaticComplexity() );
        }

    },
    @UsusTreeColumn( header = "Avg. ML", align = RIGHT, weight = 40 )
    AVG_ML( false, true ) {
        public String getLabel( IAverageMetrics metrics ) {
            return String.valueOf( metrics.getAverageMethodLength() );
        }
    },
    @UsusTreeColumn( header = "Avg. methods in class", align = RIGHT, weight = 20 )
    AVG_NUMBER_OF_METHOS_IN_CLASS( false, true ) {
        public String getLabel( IAverageMetrics metrics ) {
            return String.valueOf( metrics.getAverageNumberOfMethodsInClass() );
        }
    };

    private final boolean hasImage;
    private final boolean hasText;

    AverageMetricsColumns( boolean hasImage, boolean hasText ) {
        this.hasImage = hasImage;
        this.hasText = hasText;
    }

    public boolean hasImage() {
        return hasImage;
    }

    public boolean isHasText() {
        return hasText;
    }
}
