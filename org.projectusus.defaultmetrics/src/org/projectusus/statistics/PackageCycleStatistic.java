package org.projectusus.statistics;

import java.util.ArrayList;
import java.util.List;

import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.basis.CodeStatistic;
import org.projectusus.core.basis.Hotspot;
import org.projectusus.core.filerelations.internal.model.PackageRelations;

public class PackageCycleStatistic extends DefaultStatistic {

    private static String isisMetrics_pc = "Packages in cycles";

    public PackageCycleStatistic() {
        super( isisMetrics_pc, 0 );
    }

    @Override
    public CodeProportion getCodeProportion() {
        return new CodeProportion( getLabel(), getViolations(), getBasis(), getHotspots(), false );
    }

    public CodeStatistic getBasis() {
        return numberOfPackages();
    }

    @Override
    public int getViolations() {
        return new PackageRelations().getPackageCycles().numberOfPackagesInAnyCycles();
    }

    @Override
    public List<Hotspot> getHotspots() {
        return new ArrayList<Hotspot>();
    }

}
