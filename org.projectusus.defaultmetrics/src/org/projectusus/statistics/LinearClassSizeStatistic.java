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
    private static String isisMetrics_kg = "Class size (linear)"; //$NON-NLS-1$

    private double linearViolations = 0.0;

    public LinearClassSizeStatistic() {
        super( isisMetrics_kg, codeProportionUnit_CLASS_label, KG_LIMIT );
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
    public double getLevel() {
        return calculateLevel( linearViolations, getBasis() );
    }

}
