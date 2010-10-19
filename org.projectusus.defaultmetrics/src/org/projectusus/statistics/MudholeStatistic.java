package org.projectusus.statistics;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.CockpitExtension;

public class MudholeStatistic extends CockpitExtension {

    private static int KG_LIMIT = 20;
    private ACDStatistic acdStatistic = new ACDStatistic();

    // private CyclomaticComplexityStatistic ccStatistic = new CyclomaticComplexityStatistic();

    public MudholeStatistic() {
        super( codeProportionUnit_CLASS_label, KG_LIMIT );
    }

    @Override
    public void inspectClass( SourceCodeLocation location, MetricsResults results ) {
        addResult( location, results.getIntValue( MetricsResults.METHODS ) );
        acdStatistic.inspectClass( location, results );
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
