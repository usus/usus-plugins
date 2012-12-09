package org.projectusus.statistics;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.CockpitExtension;

/**
 * Implements a linear measurement schema for class size. Classes with more than 20 methods are measured. 21 methods counts 0.05, 40 methods counts 1, and so on.
 * 
 * @author Nicole Rauch
 * 
 */
public class LinearClassSizeStatistic extends CockpitExtension {

    public static final int KG_LIMIT = 12;

    private static final String DESCRIPTION = String.format(
            "Hotspots are classes with more than %d methods.\nRating function: f(value) = 1/%d value - 1", new Integer( KG_LIMIT ), new Integer( KG_LIMIT ) ); //$NON-NLS-1$

    private double linearViolations = 0.0;

    public LinearClassSizeStatistic() {
        super( codeProportionUnit_CLASS_label, KG_LIMIT );
    }

    @Override
    public void inspectClass( SourceCodeLocation location, MetricsResults results ) {
        int classSize = valueForClass( results );
        addResult( location, classSize );
        int surplusMethods = classSize - KG_LIMIT;
        if( surplusMethods > 0 ) {
            linearViolations += ((double)surplusMethods / KG_LIMIT);
        }
    }

    public int valueForClass( MetricsResults results ) {
        return results.getIntValue( MetricsResults.METHODS );
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
        return getLabel() + ": " + DESCRIPTION; //$NON-NLS-1$
    }

    @Override
    protected String getTooltip() {
        return "The underlying metric determines the number of static and nonstatic methods and initializers in each class.\n" //$NON-NLS-1$
                + "The method visibility is not taken into account.\n" + DESCRIPTION; //$NON-NLS-1$
    }
}
