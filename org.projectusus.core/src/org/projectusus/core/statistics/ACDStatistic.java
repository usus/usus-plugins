package org.projectusus.core.statistics;

import static org.projectusus.core.internal.util.CoreTexts.isisMetrics_acd;

import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.basis.CodeStatistic;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;

public class ACDStatistic extends DefaultStatistic {

    public ACDStatistic( JavaModelPath path ) {
        super( isisMetrics_acd, path, calculateCcdLimit( new ClassCountVisitor().visitAndReturn().getClassCount() ) );
    }

    public ACDStatistic() {
        super( isisMetrics_acd, calculateCcdLimit( new ClassCountVisitor().visitAndReturn().getClassCount() ) );
    }

    @Override
    public void inspectClass( SourceCodeLocation location, MetricsResults results ) {
        addViolation( location, results.getIntValue( MetricsResults.CCD, 1 ) );
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

    @Override
    public CodeProportion getCodeProportion() {
        double levelValue = 100.0 - 100.0 * getRelativeACD();
        return new CodeProportion( getLabel(), getViolations(), getBasis(), levelValue, getHotspots(), true );
    }

    public ACDStatistic visitAndReturn() {
        visit();
        return this;
    }

}
