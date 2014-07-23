package org.projectusus.statistics;

import static org.projectusus.core.basis.LocationType.PROJECT;

import org.projectusus.core.basis.LocationType;
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

    private static int PKG_LIMIT = 12;

    private double linearViolations = 0.0;

    public LinearPackageLocationSizeStatistic() {
        super( codeProportionUnit_PACKAGE_PER_PROJECT_label, PKG_LIMIT );
    }

    @Override
    public void inspectPackage( Packagename pkg, MetricsResults results ) {
        int pkgSize = valueForPackage( results );
        addResult( pkg, pkgSize, pkg.getClassesInPackage() );
        int surplusClasses = pkgSize - PKG_LIMIT;
        if( surplusClasses > 0 ) {
            linearViolations += ((double)surplusClasses / PKG_LIMIT);
        }
    }

    public int valueForPackage( MetricsResults results ) {
        return results.getIntValue( MetricsResults.CLASSES );
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
    protected String getTooltip() {
        return "The underlying metric determines the number of classes in each package of a project.\n" //$NON-NLS-1$
                + "If a package occurs in multiple projects, it is counted several times.\n" //$NON-NLS-1$
                + "The class visibility is not taken into account.\n" + getDescription(); //$NON-NLS-1$
    }

    @Override
    protected LocationType getLocationType() {
        return PROJECT;
    }

    @Override
    protected String hotspotsAreUnits() {
        return format( "with more than %d classes.", PKG_LIMIT );
    }
}
