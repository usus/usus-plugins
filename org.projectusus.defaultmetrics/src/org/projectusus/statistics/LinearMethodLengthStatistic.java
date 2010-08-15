package org.projectusus.statistics;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultCockpitExtension;

public class LinearMethodLengthStatistic extends DefaultCockpitExtension {

    private static int ML_LIMIT = 9;
    
    private static final String label = "Method length (linear)"; //$NON-NLS-1$
    private static final String description = label + " [0: <= %d | 1: %d]"; //$NON-NLS-1$

    private double linearViolations = 0.0;

    private static String metricsDescription() {
        return String.format( description, new Integer( ML_LIMIT ), new Integer( 2 * ML_LIMIT ) );
    }

    public LinearMethodLengthStatistic() {
        super( label, metricsDescription(), codeProportionUnit_METHOD_label, ML_LIMIT );
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
