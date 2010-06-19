package org.projectusus.core.internal.proportions.rawdata;

import org.projectusus.core.basis.CodeProportionUnit;
import org.projectusus.core.basis.CodeStatistic;
import org.projectusus.core.filerelations.model.Packagename;

public class PackageCountVisitor extends DefaultMetricsResultVisitor implements CodeStatisticCalculator {

    public PackageCountVisitor() {
        super();
    }

    public int getPackageCount() {
        return Packagename.getAll().size();
    }

    public CodeStatistic getCodeStatistic() {
        return new CodeStatistic( CodeProportionUnit.PACKAGE, getPackageCount() );
    }

}
