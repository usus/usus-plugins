package org.projectusus.statistics;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultCockpitExtension;

/**
 * Implements a linear measurement schema for class size. Classes with more than 20 methods are measured. 21 methods counts 0.05, 40 methods counts 1, and so on.
 * 
 * @author Nicole Rauch
 * 
 */
public class LinearClassSizeStatistic extends DefaultCockpitExtension {

    private static int KG_LIMIT = 12;

    private double linearViolations = 0.0;

    public LinearClassSizeStatistic() {
        super( codeProportionUnit_CLASS_label, KG_LIMIT );
    }

    @Override
    public void inspectClass( SourceCodeLocation location, MetricsResults results ) {
        int classSize = results.getIntValue( MetricsResults.METHODS );
        addViolation( location, classSize );
        int surplusMethods = classSize - KG_LIMIT;
        if( surplusMethods > 0 ) {
            linearViolations += ((double)surplusMethods / KG_LIMIT);
        }
    }

    @Override
    public double getAverage() {
        return calculateAverage( linearViolations, getBasis() );
    }

    @Override
    public String getLabel() {
        return "Class size"; //$NON-NLS-1$
    }

    @Override
    protected String getDescription() {
        String description = getLabel() + ": Hotspots are classes with more than %d methods. Rating function: f(value) = 1/%d value - 1"; //$NON-NLS-1$
        return String.format( description, new Integer( KG_LIMIT ), new Integer( KG_LIMIT ) );
    }
}
