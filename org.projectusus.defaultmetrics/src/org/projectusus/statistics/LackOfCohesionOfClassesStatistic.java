package org.projectusus.statistics;

import java.util.List;
import java.util.Set;

import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.basis.CodeStatistic;
import org.projectusus.core.basis.Hotspot;
import org.projectusus.core.basis.SinglePackageHotspot;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.IntraPackageComponents;
import org.projectusus.core.filerelations.model.PackageRelations;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.core.statistics.CockpitExtension;
import org.projectusus.core.statistics.visitors.PackageCountVisitor;

import ch.akuhn.foreach.Collect;
import ch.akuhn.foreach.ForEach;
import ch.akuhn.foreach.Select;

public class LackOfCohesionOfClassesStatistic extends CockpitExtension {

    private static final int LCOC_LIMIT = 1;

    private static final String DESCRIPTION = String.format(
            "Hotspots are methods with a LCOC greater than %d.\n Rating function: f(value) = 1/%d value - 1", new Integer( LCOC_LIMIT ), new Integer( LCOC_LIMIT ) ); //$NON-NLS-1$

    public LackOfCohesionOfClassesStatistic() {
        super( "", 0 ); //$NON-NLS-1$
    }

    @Override
    public CodeProportion getCodeProportion() {
        CodeStatistic basisStatistic = getBasisStatistic();

        IntraPackageComponents intraPackageComponents = new IntraPackageComponents();
        int setCount = intraPackageComponents.getComponents().size();
        double level = average( setCount, basisStatistic.getValue() );

        List<Hotspot> hotspots = createHotspots( intraPackageComponents );
        return new CodeProportion( getLabel(), getDescription(), getTooltip(), hotspots.size(), basisStatistic, level, hotspots, getHistogram(), getLocationType() );
    }

    private double average( int setCount, int baseCount ) {
        if( (double)baseCount == 0 ) {
            return 0.0;
        }
        return (double)setCount / (double)baseCount;
    }

    private List<Hotspot> createHotspots( IntraPackageComponents intraPackageComponents ) {

        List<List<Set<ClassDescriptor>>> listen = zusammenhangskomponentenListenFuerMehrteiligePackages( intraPackageComponents );

        for( Collect<List<Set<ClassDescriptor>>> element : ForEach.collect( listen ) ) {
            Set<ClassDescriptor> menge = element.value.iterator().next();
            Packagename pkg = menge.iterator().next().getPackagename();
            element.yield = new SinglePackageHotspot( pkg, element.value.size(), pkg.getOSPath(), pkg.getClassesInPackage() );
        }
        return ForEach.result();
    }

    private List<List<Set<ClassDescriptor>>> zusammenhangskomponentenListenFuerMehrteiligePackages( IntraPackageComponents intraPackageComponents ) {
        for( Select<List<Set<ClassDescriptor>>> entry : ForEach.select( intraPackageComponents.getSetsPerPackage().values() ) ) {
            entry.yield = entry.value.size() > LCOC_LIMIT;
        }
        return ForEach.<List<List<Set<ClassDescriptor>>>> result();
    }

    @Override
    public CodeStatistic getBasisStatistic() {
        return new PackageCountVisitor().visitAndReturn().getCodeStatistic();
    }

    @Override
    public int getBasis() {
        return getBasisStatistic().getValue();
    }

    @Override
    public int getViolations() {
        return new PackageRelations().getPackageCycles().numberOfPackagesInAnyCycles();
    }

    @Override
    public String getLabel() {
        return "Lack of cohesion of classes"; //$NON-NLS-1$
    }

    @Override
    protected String getDescription() {
        return getLabel() + ": " + DESCRIPTION; //$NON-NLS-1$
    }

    @Override
    protected String getTooltip() {
        return "This statistic describes the number of unconnected groups of classes in a package.\n" + DESCRIPTION;
    }
}
