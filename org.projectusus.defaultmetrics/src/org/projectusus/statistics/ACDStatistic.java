package org.projectusus.statistics;

import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultCockpitExtension;
import org.projectusus.core.statistics.visitors.ClassCountVisitor;

public class ACDStatistic extends DefaultCockpitExtension {

    private static final String DESCRIPTION = String.format(
            "Hotspots are classes with a CCD greater than %d.", new Integer( calculateCcdLimit( new ClassCountVisitor().visitAndReturn().getClassCount() ) ) ); //$NON-NLS-1$

    public ACDStatistic() {
        super( codeProportionUnit_CLASS_label, calculateCcdLimit( new ClassCountVisitor().visitAndReturn().getClassCount() ) );
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
        return new CodeProportion( getLabel(), getDescription(), getTooltip(), getViolations(), getBasisStatistic(), 100 * getRelativeACD(), getHotspots() );
    }

    @Override
    public String getLabel() {
        return "Average component dependency"; //$NON-NLS-1$
    }

    @Override
    protected String getDescription() {
        return getLabel() + ": " + DESCRIPTION; //$NON-NLS-1$
    }

    @Override
    protected String getTooltip() {
        return "The underlying metric is called Cumulative Component Dependency (CCD). It determines for each class the amount of other classes it knows.\n" //$NON-NLS-1$
                + "The corresponding statistic uses the average CCD of all classes in percent, the so-called Average Component Dependency (ACD).\n" + //$NON-NLS-1$
                "A class is regarded as hotspot when its CCD exceeds a value that depends on the project size.\n" //$NON-NLS-1$
                + "For small projects, a CCD that equals 15 % of the total number of classes is acceptable, while classes in large projects should not exceed a value of 5 %.\n" + DESCRIPTION; //$NON-NLS-1$
    }
}
