package org.projectusus.core.statistics.visitors;

import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultMetricsResultVisitor;

public class CyclomaticComplexityCountVisitor extends DefaultMetricsResultVisitor {

    private int violationSum = 0;

    public CyclomaticComplexityCountVisitor() {
        super();
    }

    public CyclomaticComplexityCountVisitor( JavaModelPath path ) {
        super( path );
    }

    public CyclomaticComplexityCountVisitor visitAndReturn() {
        visit();
        return this;
    }

    public int getMetricsSum() {
        return violationSum;
    }

    @Override
    public void inspectMethod( @SuppressWarnings( "unused" ) SourceCodeLocation location, MetricsResults results ) {
        violationSum += results.getIntValue( MetricsResults.CC, 1 );
    }
}
