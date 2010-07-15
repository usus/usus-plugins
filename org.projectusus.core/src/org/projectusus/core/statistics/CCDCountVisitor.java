package org.projectusus.core.statistics;

import java.util.List;

import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.basis.CodeStatistic;
import org.projectusus.core.basis.Hotspot;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;

public class CCDCountVisitor extends DefaultMetricsResultVisitor implements CodeStatisticCalculator {

    private static final String isisMetrics_acd = "Average component dependency";

    private int violationSum = 0;

    public CCDCountVisitor( JavaModelPath path ) {
        super( isisMetrics_acd, path );
    }

    @Override
    public void inspectClass( SourceCodeLocation location, MetricsResults results ) {
        violationSum += results.getIntValue( MetricsResults.CCD, 1 );
    }

    public double getRelativeACD() {
        int numberOfClasses = new ClassCountVisitor().visitAndReturn().getClassCount();
        if( numberOfClasses == 0 ) {
            return 0.0;
        }
        return getMetricsSum() / (double)(numberOfClasses * numberOfClasses);
    }

    public static int calculateCcdLimit( int classCount ) {
        // int classCount = new ClassCountVisitor().getClassCount();
        double log_5_classCount = Math.log( classCount ) / Math.log( 5 );
        double factor = 1.5 / Math.pow( 2, log_5_classCount );
        double limit = factor * classCount;
        return (int)limit;
    }

    public CodeStatistic getBasis() {
        return numberOfClasses();
    }

    public CCDCountVisitor visitAndReturn() {
        visit();
        return this;
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

    public CodeStatistic getCodeStatistic() {
        return null;
    }

}
