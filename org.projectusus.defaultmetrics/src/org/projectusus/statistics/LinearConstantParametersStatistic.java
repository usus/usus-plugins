package org.projectusus.statistics;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.CockpitExtension;

public class LinearConstantParametersStatistic extends CockpitExtension {

    public static final int CP_LIMIT = 0;

    private double linearViolations = 0.0;

    public LinearConstantParametersStatistic() {
        super( codeProportionUnit_METHOD_label, CP_LIMIT );
    }

    @Override
    public void inspectMethod( SourceCodeLocation location, MetricsResults results ) {
        int cpValue = valueForMethod( results );
        addResult( location, cpValue );
        if( cpValue > 0 ) {
            linearViolations += cpValue;
        }
    }

    public int valueForMethod( MetricsResults results ) {
        return results.getIntValue( MetricsResults.CP, 1 );
    }

    @Override
    public double getAverage() {
        return calculateAverage( linearViolations, getBasis() );
    }

    @Override
    public String getLabel() {
        return "Constant Parameters"; //$NON-NLS-1$
    }

    @Override
    protected String getTooltip() {
        return "The underlying metric determines the number of the constant parameters null, false and true in all method invocations in a method body.\n" //$NON-NLS-1$
                + getDescription();
    }

    @Override
    protected String hotspotsAreUnits() {
        return format( "with a CP greater than %d.", CP_LIMIT );
    }
}
