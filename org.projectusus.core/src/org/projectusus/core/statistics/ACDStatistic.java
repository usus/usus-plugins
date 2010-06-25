package org.projectusus.core.statistics;

import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;

public class ACDStatistic extends DefaultStatistic {

    public ACDStatistic( JavaModelPath path ) {
        super( path, calculateCcdLimit( new ClassCountVisitor().visit().getClassCount() ) );
    }

    public ACDStatistic() {
        super( calculateCcdLimit( new ClassCountVisitor().visit().getClassCount() ) );
    }

    @Override
    public void inspectClass( SourceCodeLocation location, MetricsResults results ) {
        addViolation( location, results.getIntValue( MetricsResults.CCD, 1 ) );
    }

    public double getRelativeACD() {
        int numberOfClasses = new ClassCountVisitor().visit().getClassCount();
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

    @Override
    public ACDStatistic visit() {
        super.visit();
        return this;
    }

}
