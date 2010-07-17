package org.projectusus.core.statistics.visitors;

import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultMetricsResultVisitor;

public class MethodLengthCountVisitor extends DefaultMetricsResultVisitor {

    private int violationSum = 0;

    public MethodLengthCountVisitor() {
        super();
    }

    public MethodLengthCountVisitor( JavaModelPath path ) {
        super( path );
    }

    public int getMetricsSum() {
        return violationSum;
    }

    @Override
    public void inspectMethod( @SuppressWarnings( "unused" ) SourceCodeLocation location, MetricsResults result ) {
        violationSum += result.getIntValue( MetricsResults.ML );
    }

    public MethodLengthCountVisitor visitAndReturn() {
        visit();
        return this;
    }
}
