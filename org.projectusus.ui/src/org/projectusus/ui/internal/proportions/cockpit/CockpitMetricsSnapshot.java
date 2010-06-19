package org.projectusus.ui.internal.proportions.cockpit;

import java.util.ArrayList;
import java.util.List;

import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.internal.UsusCorePlugin;

public class CockpitMetricsSnapshot {

    private static List<CodeProportion> entries = new ArrayList<CodeProportion>();

    public static String trendFor( CodeProportion element ) {
        for( CodeProportion codeProportion : entries ) {
            if( codeProportion.getMetric() == element.getMetric() ) {
                double diff = element.getLevel() - codeProportion.getLevel();
                if( diff == 0.0 ) {
                    return " ";
                }
                return diff < 0 ? "-" : "+";
            }
        }
        return " ";
    }

    public static void click() {
        entries = UsusCorePlugin.getUsusModel().getCodeProportions();
    }
}
