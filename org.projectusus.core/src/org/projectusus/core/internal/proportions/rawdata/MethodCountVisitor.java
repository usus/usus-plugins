package org.projectusus.core.internal.proportions.rawdata;

import org.projectusus.core.basis.CodeProportionUnit;
import org.projectusus.core.basis.CodeStatistic;

public class MethodCountVisitor extends DefaultMetricsResultVisitor implements CodeStatisticCalculator {

    private int methodCount = 0;

    public MethodCountVisitor() {
        super();
    }

    public MethodCountVisitor( JavaModelPath path ) {
        super( path );
    }

    @Override
    public void inspect( @SuppressWarnings( "unused" ) MethodRawData methodRawData ) {
        methodCount++;
    }

    public int getMethodCount() {
        visit();
        return methodCount;
    }

    public CodeStatistic getCodeStatistic() {
        return new CodeStatistic( CodeProportionUnit.METHOD, getMethodCount() );
    }

}
