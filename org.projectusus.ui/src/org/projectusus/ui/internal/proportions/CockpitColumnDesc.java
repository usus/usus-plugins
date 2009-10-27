// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions;

import static org.projectusus.ui.internal.util.UITexts.cockpitColumnDesc_cases;
import static org.projectusus.ui.internal.util.UITexts.cockpitColumnDesc_indicator;
import static org.projectusus.ui.internal.util.UITexts.cockpitColumnDesc_sqi;
import static org.projectusus.ui.internal.util.UITexts.cockpitColumnDesc_violations;

enum CockpitColumnDesc {

    INDICATOR( cockpitColumnDesc_indicator, 58, true ), //
    SQI( cockpitColumnDesc_sqi, 14, false ), //
    VIOLATIONS( cockpitColumnDesc_violations, 14, false ), //
    CASES( cockpitColumnDesc_cases, 14, false );

    private final String headLabel;
    private final int weight;
    private final boolean hasImage;

    CockpitColumnDesc( String headLabel, int weight, boolean hasImage ) {
        this.headLabel = headLabel;
        this.weight = weight;
        this.hasImage = hasImage;
    }

    int getWeight() {
        return weight;
    }

    String getHeadLabel() {
        return headLabel;
    }

    boolean isHasImage() {
        return hasImage;
    }
}
