package org.projectusus.core.statistics;

import org.projectusus.core.basis.CodeProportionUnit;
import org.projectusus.core.basis.CodeStatistic;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;

public class ClassCountVisitor extends DefaultMetricsResultVisitor implements CodeStatisticCalculator {

    private int classCount = 0;

    public ClassCountVisitor( JavaModelPath path ) {
        super( path );
    }

    public ClassCountVisitor() {
        super();
    }

    @Override
    public void inspectClass( @SuppressWarnings( "unused" ) SourceCodeLocation location, @SuppressWarnings( "unused" ) MetricsResults result ) {
        classCount++;
    }

    public int getClassCount() {
        return classCount;
    }

    public CodeStatistic getCodeStatistic() {
        return new CodeStatistic( CodeProportionUnit.CLASS, getClassCount() );
    }

    @Override
    public ClassCountVisitor visit() {
        super.visit();
        return this;
    }

}
