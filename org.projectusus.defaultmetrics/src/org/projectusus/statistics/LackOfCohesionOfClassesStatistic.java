package org.projectusus.statistics;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

public class LackOfCohesionOfClassesStatistic extends CockpitExtension {

    private static final int LCOC_LIMIT = 1;

    public LackOfCohesionOfClassesStatistic() {
        super( codeProportionUnit_PACKAGE_label, LCOC_LIMIT ); // $NON-NLS-1$
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
        Stream<List<Set<ClassDescriptor>>> listen = zusammenhangskomponentenListenFuerMehrteiligePackages( intraPackageComponents );

        return listen.map( this::createPackageHotspot ).collect( Collectors.toList() );
    }

    private SinglePackageHotspot createPackageHotspot( List<Set<ClassDescriptor>> element ) {
        Set<ClassDescriptor> firstClassSet = element.stream().findFirst().get();
        ClassDescriptor firstClass = firstClassSet.stream().findFirst().get();
        Packagename hotspotPackage = firstClass.getPackagename();
        return new SinglePackageHotspot( hotspotPackage, element.size(), hotspotPackage.getOSPath(), hotspotPackage.getClassesInPackage() );
    }

    private Stream<List<Set<ClassDescriptor>>> zusammenhangskomponentenListenFuerMehrteiligePackages( IntraPackageComponents intraPackageComponents ) {
        return intraPackageComponents.getSetsPerPackage().values().stream().filter( list -> list.size() > LCOC_LIMIT );
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
    protected String getTooltip() {
        return "This statistic describes the number of unconnected groups of classes in a package.\n" + getDescription();
    }

    @Override
    protected String hotspotsAreUnits() {
        return format( "with a LCOC greater than %d.", LCOC_LIMIT );
    }
}
