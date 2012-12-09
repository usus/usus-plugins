package org.projectusus.statistics;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.CockpitExtension;

public class LinearCyclomaticComplexityStatistic extends CockpitExtension {

    private static int CC_LIMIT = 4;

    private static final String DESCRIPTION = String.format(
            "Hotspots are methods with a CC greater than %d.\nRating function: f(value) = 1/%d value - 1", new Integer( CC_LIMIT ), new Integer( CC_LIMIT ) ); //$NON-NLS-1$

    private double linearViolations = 0.0;

    public LinearCyclomaticComplexityStatistic() {
        super( codeProportionUnit_METHOD_label, CC_LIMIT );
    }

    @Override
    public void inspectMethod( SourceCodeLocation location, MetricsResults results ) {
        int ccValue = valueForMethod( results );
        addResult( location, ccValue );
        int exceedingCC = ccValue - CC_LIMIT;
        if( exceedingCC > 0 ) {
            linearViolations += ((double)exceedingCC / CC_LIMIT);
        }
    }

    public int valueForMethod( MetricsResults results ) {
        return results.getIntValue( MetricsResults.CC, 1 );
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
        return getLabel() + ": " + DESCRIPTION; //$NON-NLS-1$
    }

    @Override
    protected String getTooltip() {
        return "The underlying metric determines the number of possible branches in the execution path through a method body.\n" //$NON-NLS-1$
                + "An empty method body has a cyclomatic complexity of 1. Each occurrence of a branching language element,\n" //$NON-NLS-1$
                + "e.g. if, while, catch or the conditional operators && or ||, increases this number by 1.\n" + DESCRIPTION; //$NON-NLS-1$
    }
}
