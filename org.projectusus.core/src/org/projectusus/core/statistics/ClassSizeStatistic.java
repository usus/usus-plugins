package org.projectusus.core.statistics;

import static org.projectusus.core.internal.util.CoreTexts.isisMetrics_kg;

import org.projectusus.core.basis.CodeStatistic;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;

public class ClassSizeStatistic extends DefaultStatistic {

    private static int KG_LIMIT = 20;

    public ClassSizeStatistic() {
        super( isisMetrics_kg, KG_LIMIT );
    }

    public ClassSizeStatistic( JavaModelPath path ) {
        super( isisMetrics_kg, path, KG_LIMIT );
    }

    @Override
    public void inspectClass( SourceCodeLocation location, MetricsResults results ) {
        addViolation( location, results.getIntValue( MetricsResults.METHODS ) );
    }

    public CodeStatistic getBasis() {
        return numberOfClasses();
    }

}
