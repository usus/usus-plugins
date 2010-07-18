package org.projectusus.statistics;

import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.basis.CodeStatistic;
import org.projectusus.core.filerelations.internal.model.PackageRelations;
import org.projectusus.core.statistics.DefaultCockpitExtension;
import org.projectusus.core.statistics.visitors.PackageCountVisitor;

public class PackageCycleStatistic extends DefaultCockpitExtension {

    private static String isisMetrics_pc = "Packages in cycles";

    public PackageCycleStatistic() {
        super( isisMetrics_pc, "", 0 );
    }

    @Override
    public CodeProportion getCodeProportion() {
        return new CodeProportion( getLabel(), getViolations(), getBasisStatistic(), getLevel() );
    }

    @Override
    public CodeStatistic getBasisStatistic() {
        return new PackageCountVisitor().visitAndReturn().getCodeStatistic();
    }

    @Override
    public int getViolations() {
        return new PackageRelations().getPackageCycles().numberOfPackagesInAnyCycles();
    }
}
