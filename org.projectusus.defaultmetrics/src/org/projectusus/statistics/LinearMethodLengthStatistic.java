package org.projectusus.statistics;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultCockpitExtension;

public class LinearMethodLengthStatistic extends DefaultCockpitExtension {

    private static int ML_LIMIT = 9;

    private double linearViolations = 0.0;

    public LinearMethodLengthStatistic() {
        super( codeProportionUnit_METHOD_label, ML_LIMIT );
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
    public double getAverage() {
        return calculateAverage( linearViolations, getBasis() );
    }

    @Override
    public String getLabel() {
        return "Method length"; //$NON-NLS-1$
    }

    @Override
    protected String getDescription() {
        String description = getLabel() + ": Hotspots are methods with more than %d statements. Rating function: f(value) = 1/%d value - 1"; //$NON-NLS-1$    
        return String.format( description, new Integer( ML_LIMIT ), new Integer( ML_LIMIT ) );
    }

}
