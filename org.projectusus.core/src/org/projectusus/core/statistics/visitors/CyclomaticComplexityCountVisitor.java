package org.projectusus.core.statistics.visitors;

import java.util.List;

import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.basis.CodeStatistic;
import org.projectusus.core.basis.Hotspot;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultMetricsResultVisitor;

public class CyclomaticComplexityCountVisitor extends DefaultMetricsResultVisitor {

    private static String isisMetrics_cc = "Cyclomatic complexity count";

    private int violationSum = 0;

    public CyclomaticComplexityCountVisitor() {
        super( );
    }

    public CyclomaticComplexityCountVisitor( JavaModelPath path ) {
        super( path );
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
