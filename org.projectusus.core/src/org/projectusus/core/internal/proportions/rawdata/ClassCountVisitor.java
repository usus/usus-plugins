package org.projectusus.core.internal.proportions.rawdata;

import org.projectusus.core.basis.CodeProportionUnit;
import org.projectusus.core.basis.CodeStatistic;

public class ClassCountVisitor extends DefaultMetricsResultVisitor implements CodeStatisticCalculator {

    private int classCount = 0;

    public ClassCountVisitor( JavaModelPath path ) {
        super( path );
    }

    public ClassCountVisitor() {
        super();
    }

    @Override
    public void inspect( @SuppressWarnings( "unused" ) ClassRawData classRawData ) {
        classCount++;
    }

    public int getClassCount() {
        visit();
        return classCount;
    }

    public CodeStatistic getCodeStatistic() {
        return new CodeStatistic( CodeProportionUnit.CLASS, getClassCount() );
    }

}