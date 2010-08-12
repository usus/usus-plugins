package org.projectusus.statistics;

import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultCockpitExtension;
import org.projectusus.core.statistics.visitors.ClassCountVisitor;

public class ACDStatistic extends DefaultCockpitExtension {

    private static final String label = "Average component dependency (0: <= %d)"; //$NON-NLS-1$

    private static String metricsLabel() {
        return String.format( label, new Integer( calculateCcdLimit( new ClassCountVisitor().visitAndReturn().getClassCount() ) ) );
    }

    public ACDStatistic() {
        super( metricsLabel(), codeProportionUnit_CLASS_label, calculateCcdLimit( new ClassCountVisitor().visitAndReturn().getClassCount() ) );
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
        double log_5_classCount = Math.log( classCount ) / Math.log( 5 );
        double factor = 1.5 / Math.pow( 2, log_5_classCount );
        double limit = factor * classCount;
        return (int)limit;
    }

    @Override
    public CodeProportion getCodeProportion() {
        double levelValue = 100.0 - 100.0 * getRelativeACD();
        return new CodeProportion( getLabel(), getViolations(), getBasisStatistic(), levelValue, getHotspots() );
    }
}
