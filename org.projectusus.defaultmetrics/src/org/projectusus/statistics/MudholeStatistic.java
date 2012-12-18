package org.projectusus.statistics;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.CockpitExtension;

public class MudholeStatistic extends CockpitExtension {

    private static final int MUDHOLE_LIMIT = 49;

    // class metrics
    private LinearClassSizeStatistic classSize = new LinearClassSizeStatistic();

    // method metrics
    private LinearMethodLengthStatistic methodLength = new LinearMethodLengthStatistic();
    private LinearCyclomaticComplexityStatistic cc = new LinearCyclomaticComplexityStatistic();

    private int currentClassValue;

    public MudholeStatistic() {
        super( codeProportionUnit_METHOD_label, MUDHOLE_LIMIT );
    }

    @Override
    public void inspectClass( SourceCodeLocation location, MetricsResults results ) {
        int result = classSize.valueForClass( results );
        currentClassValue = result >= LinearClassSizeStatistic.KG_LIMIT ? result : 0;
    }

    @Override
    public void inspectMethod( SourceCodeLocation location, MetricsResults results ) {
        int methodValue = methodLength.valueForMethod( results ) * cc.valueForMethod( results );
        methodValue += currentClassValue;
        addResult( location, methodValue );
    }

    @Override
    public String getLabel() {
        return "Mudholes"; //$NON-NLS-1$
    }

    @Override
    protected String hotspotsAreUnits() {
        return format( "where the product of method length and cyclomatic complexity (increased by the class size if the latter exceeds %d) is greater than %d.",
                LinearClassSizeStatistic.KG_LIMIT, MUDHOLE_LIMIT );
    }

    @Override
    protected String getRatingFunction() {
        return format( "\nRating function: f(ML) * f(CC) + f(KG) > %d", MUDHOLE_LIMIT ); //$NON-NLS-1$
    }

}
