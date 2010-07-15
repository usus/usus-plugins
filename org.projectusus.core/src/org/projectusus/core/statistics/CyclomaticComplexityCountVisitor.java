package org.projectusus.core.statistics;

import java.util.List;

import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.basis.CodeStatistic;
import org.projectusus.core.basis.Hotspot;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;

public class CyclomaticComplexityCountVisitor extends DefaultMetricsResultVisitor implements CodeStatisticCalculator {

    private static String isisMetrics_cc = "Cyclomatic complexity count";

    private int violationSum = 0;

    public CyclomaticComplexityCountVisitor() {
        super( isisMetrics_cc );
    }

    public CyclomaticComplexityCountVisitor( JavaModelPath path ) {
        super( isisMetrics_cc, path );
    }

    // drin!
    public CyclomaticComplexityCountVisitor visitAndReturn() {
        visit();
        return this;
    }

    // drin!
    public int getMetricsSum() {
        return violationSum;
    }

    @Override
    public void inspectMethod( SourceCodeLocation location, MetricsResults results ) {
        addViolation( location, results.getIntValue( MetricsResults.CC, 1 ) );
    }

    protected void addViolation( SourceCodeLocation location, int count ) {
        violationSum += count;
    }

    public int getViolations() {
        return 0;
    }

    public List<Hotspot> getHotspots() {
        return null;
    }

    public CodeProportion getCodeProportion() {
        return null;
    }

    public CodeStatistic getBasis() {
        return numberOfMethods();
    }

    public CodeStatistic getCodeStatistic() {
        return null;
    }
}
