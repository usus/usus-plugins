package org.projectusus.statistics;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.core.statistics.CockpitExtension;

/**
 * Implements a linear measurement schema for class size. Classes with more than 20 methods are measured. 21 methods counts 0.05, 40 methods counts 1, and so on.
 * 
 * @author Nicole Rauch
 * 
 */
public class LinearPackageLocationSizeStatistic extends CockpitExtension {

    private static int PKG_LIMIT = 2;

    private static final String DESCRIPTION = String.format(
            "Hotspots are packages with more than %d classes. Rating function: f(value) = 1/%d value - 1", new Integer( PKG_LIMIT ), new Integer( PKG_LIMIT ) ); //$NON-NLS-1$

    private double linearViolations = 0.0;

    public LinearPackageLocationSizeStatistic() {
        super( codeProportionUnit_PACKAGE_PER_PROJECT_label, PKG_LIMIT );
    }

    @Override
    public void inspectPackage( Packagename pkg, MetricsResults results ) {
        int pkgSize = results.getIntValue( MetricsResults.CLASSES );
        addResult( pkg, pkgSize );
        int surplusClasses = pkgSize - PKG_LIMIT;
        if( surplusClasses > 0 ) {
            linearViolations += ((double)surplusClasses / PKG_LIMIT);
        }
    }

    @Override
    public double getAverage() {
        return calculateAverage( linearViolations, getBasis() );
    }

    @Override
    public String getLabel() {
        return "Package size (per project)"; //$NON-NLS-1$
    }

    @Override
    protected String getDescription() {
        return getLabel() + ": " + DESCRIPTION; //$NON-NLS-1$
    }

    @Override
    protected String getTooltip() {
        return "The underlying metric determines the number of classes in each package.\n" //$NON-NLS-1$
                + "The class visibility is not taken into account.\n" + DESCRIPTION; //$NON-NLS-1$
    }
}
