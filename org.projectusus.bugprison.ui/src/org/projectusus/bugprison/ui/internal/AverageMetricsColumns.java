// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.bugprison.ui.internal;

import static org.projectusus.ui.viewer.ColumnAlignment.RIGHT;

import org.projectusus.bugprison.core.IAverageMetrics;
import org.projectusus.ui.viewer.IColumnDesc;
import org.projectusus.ui.viewer.UsusTreeColumn;

public enum AverageMetricsColumns implements IColumnDesc<IAverageMetrics> {
    @UsusTreeColumn( header = "Name", weight = 15 )
    NAME {
        public String getLabel( IAverageMetrics element ) {
            return element.getName();
        }
    },
    @UsusTreeColumn( header = "Avg. CC", align = RIGHT, weight = 15 )
    AVG_CC {
        public String getLabel( IAverageMetrics metrics ) {
            return String.valueOf( metrics.getAverageCyclomaticComplexity() );
        }

    },
    @UsusTreeColumn( header = "Avg. ML", align = RIGHT, weight = 40 )
    AVG_ML {
        public String getLabel( IAverageMetrics metrics ) {
            return String.valueOf( metrics.getAverageMethodLength() );
        }
    },
    @UsusTreeColumn( header = "Avg. methods in class", align = RIGHT, weight = 20 )
    AVG_NUMBER_OF_METHOS_IN_CLASS {
        public String getLabel( IAverageMetrics metrics ) {
            return String.valueOf( metrics.getAverageNumberOfMethodsInClass() );
        }
    };

    public boolean hasImage() {
        return false;
    }
}
