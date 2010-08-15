package org.projectusus.statistics;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultCockpitExtension;

public class MudholeStatistic extends DefaultCockpitExtension {

    private static int KG_LIMIT = 20;
    private ACDStatistic acdStatistic = new ACDStatistic();

    // private CyclomaticComplexityStatistic ccStatistic = new CyclomaticComplexityStatistic();

    public MudholeStatistic() {
        super( "Mudholes", "Mudholes", codeProportionUnit_CLASS_label, KG_LIMIT ); //$NON-NLS-1$ //$NON-NLS-2$
    }

    @Override
    public void inspectClass( SourceCodeLocation location, MetricsResults results ) {
        addViolation( location, results.getIntValue( MetricsResults.METHODS ) );
        acdStatistic.inspectClass( location, results );
    }

}
