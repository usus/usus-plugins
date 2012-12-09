package org.projectusus.statistics;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.CockpitExtension;

public class MudholeStatistic extends CockpitExtension {

    private static final int MUDHOLE_LIMIT = 49;
    private static final String DESCRIPTION = String.format( "Rating function: f(ML) * f(CC) + f(KG) > %d", Integer.valueOf( MUDHOLE_LIMIT ) ); //$NON-NLS-1$

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
    protected String getDescription() {
        String description = getLabel() + ": " + DESCRIPTION;
        return String.format( description, Integer.valueOf( MUDHOLE_LIMIT ) ); //$NON-NLS-1$
    }

    @Override
    protected String getTooltip() {
        return "Mudholes are classes or methods that violate several aspects at once.\n" //
                + "This mudhole statistic takes into account the method length and " //
                + "cyclomatic complexity of a method as well as the size of its class.\n" + getDescription();
    }
}
