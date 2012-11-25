package org.projectusus.statistics;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.CockpitExtension;

public class MudholeStatistic extends CockpitExtension {

    private static final int MUDHOLE_LIMIT = 49;

    // class metrics
    private ClassSizeStatistic classSize = new ClassSizeStatistic();

    // method metrics
    private MethodLengthStatistic methodLength = new MethodLengthStatistic();
    private CyclomaticComplexityStatistic cc = new CyclomaticComplexityStatistic();

    private int currentClassValue;

    public MudholeStatistic() {
        super( codeProportionUnit_METHOD_label, MUDHOLE_LIMIT );
    }

    @Override
    public void inspectClass( SourceCodeLocation location, MetricsResults results ) {
        int result = classSize.valueForClass( results );
        currentClassValue = result >= ClassSizeStatistic.KG_LIMIT ? result : 0;
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
        return "Mudholes"; //$NON-NLS-1$
    }
}
