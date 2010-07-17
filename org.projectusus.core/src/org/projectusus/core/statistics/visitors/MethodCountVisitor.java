package org.projectusus.core.statistics.visitors;

import static org.projectusus.core.internal.util.CoreTexts.codeProportionUnit_METHOD_label;

import java.util.List;

import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.basis.CodeStatistic;
import org.projectusus.core.basis.Hotspot;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.CodeStatisticCalculator;
import org.projectusus.core.statistics.DefaultMetricsResultVisitor;

public class MethodCountVisitor extends DefaultMetricsResultVisitor implements CodeStatisticCalculator {

    private int methodCount = 0;

    public MethodCountVisitor() {
        super( );
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
        return new CodeStatistic( codeProportionUnit_METHOD_label, getMethodCount() );
    }

    public MethodCountVisitor visitAndReturn() {
        super.visit();
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
