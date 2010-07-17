package org.projectusus.core.statistics.visitors;

import static org.projectusus.core.internal.util.CoreTexts.codeProportionUnit_PACKAGE_label;

import org.projectusus.core.basis.CodeStatistic;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.core.statistics.DefaultMetricsResultVisitor;

public class PackageCountVisitor extends DefaultMetricsResultVisitor {

    public PackageCountVisitor( JavaModelPath path ) {
        super( path );
    }

    public PackageCountVisitor() {
        super();
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
}
