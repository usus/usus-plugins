package org.projectusus.core.statistics.visitors;

import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultMetricsResultVisitor;

public class CCDCountVisitor extends DefaultMetricsResultVisitor {

    private int violationSum = 0;

    public CCDCountVisitor( JavaModelPath path ) {
        super( path );
    }

    @Override
    public void inspectClass( @SuppressWarnings( "unused" ) SourceCodeLocation location, MetricsResults results ) {
        violationSum += results.getIntValue( MetricsResults.CCD, 1 );
    }

    public CCDCountVisitor visitAndReturn() {
        visit();
        return this;
    }

    public int getMetricsSum() {
        return violationSum;
    }
}
