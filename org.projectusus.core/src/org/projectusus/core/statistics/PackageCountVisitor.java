package org.projectusus.core.statistics;

import static org.projectusus.core.internal.util.CoreTexts.codeProportionUnit_PACKAGE_label;

import java.util.List;

import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.basis.CodeStatistic;
import org.projectusus.core.basis.Hotspot;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.filerelations.model.Packagename;

public class PackageCountVisitor extends DefaultMetricsResultVisitor implements CodeStatisticCalculator {

    public PackageCountVisitor( JavaModelPath path ) {
        super( codeProportionUnit_PACKAGE_label, path );
    }

    public PackageCountVisitor() {
        super( codeProportionUnit_PACKAGE_label );
    }

    public int getPackageCount() {
        return Packagename.getAll().size();
    }

    public CodeStatistic getCodeStatistic() {
        return new CodeStatistic( codeProportionUnit_PACKAGE_label, getPackageCount() );
    }

    public PackageCountVisitor visitAndReturn() {
        return this;
    }

    public CodeStatistic getBasis() {
        // TODO Auto-generated method stub
        return null;
    }

    public CodeProportion getCodeProportion() {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Hotspot> getHotspots() {
        // TODO Auto-generated method stub
        return null;
    }

    public int getMetricsSum() {
        // TODO Auto-generated method stub
        return 0;
    }

    public int getViolations() {
        // TODO Auto-generated method stub
        return 0;
    }

}
