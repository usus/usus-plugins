package org.projectusus.statistics;

import static org.projectusus.core.internal.util.CoreTexts.codeProportionUnit_CLASS_label;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultCockpitExtension;

public class ClassSizeStatistic extends DefaultCockpitExtension {

    private static int KG_LIMIT = 20;
    private static String isisMetrics_kg = "Class size";

    public ClassSizeStatistic() {
        super( isisMetrics_kg, codeProportionUnit_CLASS_label, KG_LIMIT );
    }

    @Override
    public void inspectClass( SourceCodeLocation location, MetricsResults results ) {
        addViolation( location, results.getIntValue( MetricsResults.METHODS ) );
    }
}
