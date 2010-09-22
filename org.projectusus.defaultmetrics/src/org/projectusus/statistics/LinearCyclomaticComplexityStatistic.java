package org.projectusus.statistics;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultCockpitExtension;

public class LinearCyclomaticComplexityStatistic extends DefaultCockpitExtension {

    private static int CC_LIMIT = 4;

    private double linearViolations = 0.0;

    public LinearCyclomaticComplexityStatistic() {
        super( codeProportionUnit_METHOD_label, CC_LIMIT );
    }

    @Override
    public void inspectMethod( SourceCodeLocation location, MetricsResults results ) {
        int ccValue = results.getIntValue( MetricsResults.CC, 1 );
        addViolation( location, ccValue );
        int exceedingCC = ccValue - CC_LIMIT;
        if( exceedingCC > 0 ) {
            linearViolations += ((double)exceedingCC / CC_LIMIT);
        }
    }

    @Override
    public double getAverage() {
        return calculateAverage( linearViolations, getBasis() );
    }

    @Override
    public String getLabel() {
        return "Cyclomatic complexity"; //$NON-NLS-1$
    }

    @Override
    protected String getDescription() {
        String description = getLabel() + ": Hotspots are methods with a CC greater than %d. Rating function: f(value) = 1/%d value - 1"; //$NON-NLS-1$
        return String.format( description, new Integer( CC_LIMIT ), new Integer( CC_LIMIT ) );
    }
}
