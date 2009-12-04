// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.bugreport;

import static org.projectusus.ui.internal.util.UITexts.AverageMetricsColumns_CC;
import static org.projectusus.ui.internal.util.UITexts.AverageMetricsColumns_ML;
import static org.projectusus.ui.internal.util.UITexts.AverageMetricsColumns_name;
import static org.projectusus.ui.internal.util.UITexts.AverageMetricsColumns_number_of_methos_in_class;

import org.projectusus.core.internal.bugreport.IAverageMetrics;
import org.projectusus.ui.internal.viewer.IColumnDesc;

public enum AverageMetricsColumns implements IColumnDesc<IAverageMetrics> {
    NAME( AverageMetricsColumns_name, 15, false, true ) {
        public String getLabel( IAverageMetrics element ) {
            return element.getName();
        }
    },
    AVG_CC( AverageMetricsColumns_CC, 15, false, true ) {
        public String getLabel( IAverageMetrics metrics ) {
            return String.valueOf( metrics.getAverageCyclomaticComplexity() );
        }
    },
    AVG_ML( AverageMetricsColumns_ML, 40, false, true ) {
        public String getLabel( IAverageMetrics metrics ) {
            return String.valueOf( metrics.getAverageMethodLength() );
        }
    },
    AVG_NUMBER_OF_METHOS_IN_CLASS( AverageMetricsColumns_number_of_methos_in_class, 20, false, true ) {
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

    public boolean isHasText() {
        return hasText;
    }
}
