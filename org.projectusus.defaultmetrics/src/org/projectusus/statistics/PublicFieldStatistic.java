package org.projectusus.statistics;

import org.projectusus.core.IMetricsResultVisitor;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.CockpitExtension;
import org.projectusus.metrics.PublicFieldMetrics;

public class PublicFieldStatistic extends CockpitExtension {

    public PublicFieldStatistic() {
        super( IMetricsResultVisitor.codeProportionUnit_CLASS_label, 0 );
    }

    @Override
    public String getLabel() {
        return "Number of non-static, non-final public fields"; //$NON-NLS-1$
    }

    @Override
    protected String getDescription() {
        return getLabel() + ": Hotspots are classes with at least one such field"; //$NON-NLS-1$
    }

    @Override
    public void inspectClass( SourceCodeLocation location, MetricsResults results ) {
        addResult( location, results.getIntValue( PublicFieldMetrics.PUBLIC_FIELDS ) );
    }
}
