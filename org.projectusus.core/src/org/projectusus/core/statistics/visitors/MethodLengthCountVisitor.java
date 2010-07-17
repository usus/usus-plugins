package org.projectusus.core.statistics.visitors;

import java.util.List;

import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.basis.CodeStatistic;
import org.projectusus.core.basis.Hotspot;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.CodeStatisticCalculator;
import org.projectusus.core.statistics.DefaultMetricsResultVisitor;

public class MethodLengthCountVisitor extends DefaultMetricsResultVisitor implements CodeStatisticCalculator {

    private static String isisMetrics_ml = "Method length count";

    private int violationSum = 0;

    public MethodLengthCountVisitor() {
        super( );
    }

    public MethodLengthCountVisitor( JavaModelPath path ) {
        super( path );
    }

    protected void addViolation( SourceCodeLocation location, int count ) {
        violationSum += count;
    }

    public int getViolations() {
        return 0;
    }

    public int getMetricsSum() {
        return violationSum;
    }

    public List<Hotspot> getHotspots() {
        return null;
    }

    public CodeProportion getCodeProportion() {
        return null;
    }

    @Override
    public void inspectMethod( SourceCodeLocation location, MetricsResults result ) {
        addViolation( location, result.getIntValue( MetricsResults.ML ) );
    }

    public CodeStatistic getBasis() {
        return null;
    }

    public MethodLengthCountVisitor visitAndReturn() {
        visit();
        return this;
    }

    public CodeStatistic getCodeStatistic() {
        return null;
    }
}
