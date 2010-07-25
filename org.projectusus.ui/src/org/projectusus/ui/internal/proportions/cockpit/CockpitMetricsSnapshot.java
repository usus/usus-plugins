package org.projectusus.ui.internal.proportions.cockpit;

import static org.projectusus.ui.internal.AnalysisDisplayModel.displayModel;

public class CockpitMetricsSnapshot {

    public static void click() {
        displayModel().createSnapshot();
    }
}
