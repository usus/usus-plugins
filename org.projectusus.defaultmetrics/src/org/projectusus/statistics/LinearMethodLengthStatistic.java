package org.projectusus.statistics;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultCockpitExtension;

public class LinearMethodLengthStatistic extends DefaultCockpitExtension {

    private static int ML_LIMIT = 15;
    private static String isisMetrics_ml = "Method length (linear)"; //$NON-NLS-1$

    private double linearViolations = 0.0;

    public LinearMethodLengthStatistic() {
        super( isisMetrics_ml, codeProportionUnit_METHOD_label, ML_LIMIT );
    }

    @Override
    public void inspectMethod( SourceCodeLocation location, MetricsResults result ) {
        int methodLength = result.getIntValue( MetricsResults.ML );
        addViolation( location, methodLength );
        int exceedingLines = methodLength - ML_LIMIT;
        if( exceedingLines > 0 ) {
            linearViolations += ((double)exceedingLines / ML_LIMIT);
        }
    }

    @Override
    public double getLevel() {
        return calculateLevel( linearViolations, getBasis() );
    }

}
