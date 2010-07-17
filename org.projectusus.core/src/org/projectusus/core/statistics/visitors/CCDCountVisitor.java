package org.projectusus.core.statistics.visitors;

import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.basis.CodeStatistic;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultMetricsResultVisitor;

public class CCDCountVisitor extends DefaultMetricsResultVisitor {

    private static final String isisMetrics_acd = "Average component dependency";

    private int violationSum = 0;

    public CCDCountVisitor( JavaModelPath path ) {
        super( path );
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

    public int getMetricsSum() {
        return violationSum;
    }

    public CodeProportion getCodeProportion() {
        return null;
    }
}
