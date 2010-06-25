package org.projectusus.core.statistics;

import org.projectusus.core.basis.CodeProportionUnit;
import org.projectusus.core.basis.CodeStatistic;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;

public class MethodCountVisitor extends DefaultMetricsResultVisitor implements CodeStatisticCalculator {

    private int methodCount = 0;

    public MethodCountVisitor() {
        super();
    }

    public MethodCountVisitor( JavaModelPath path ) {
        super( path );
    }

    @Override
    public void inspectMethod( @SuppressWarnings( "unused" ) SourceCodeLocation location, @SuppressWarnings( "unused" ) MetricsResults results ) {
        methodCount++;
    }

    public int getMethodCount() {
        return methodCount;
    }

    public CodeStatistic getCodeStatistic() {
        return new CodeStatistic( CodeProportionUnit.METHOD, getMethodCount() );
    }

    @Override
    public MethodCountVisitor visit() {
        super.visit();
        return this;
    }

}
