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
	
    @UsusTreeColumn( weight = 15 )
    NAME("Name") {
        public String getLabel( IAverageMetrics element ) {
            return element.getName();
        }
    },
    
    @UsusTreeColumn(  align = RIGHT, weight = 15 )
    AVG_CC("Avg. CC") {
        public String getLabel( IAverageMetrics metrics ) {
            return String.valueOf( metrics.getAverageCyclomaticComplexity() );
        }

    },
    
    @UsusTreeColumn( align = RIGHT, weight = 40 )
    AVG_ML("Avg. ML") {
        public String getLabel( IAverageMetrics metrics ) {
            return String.valueOf( metrics.getAverageMethodLength() );
        }
    },
    
    @UsusTreeColumn(  align = RIGHT, weight = 20 )
    AVG_NUMBER_OF_METHOS_IN_CLASS("Avg. methods in class") {
        public String getLabel( IAverageMetrics metrics ) {
            return String.valueOf( metrics.getAverageNumberOfMethodsInClass() );
        }
    };
    
    private String header;

	AverageMetricsColumns(String header) {
		this.header = header;
	}
	
	public String getHeader() {
		return header;
	}

    public boolean hasImage() {
        return false;
    }
}
