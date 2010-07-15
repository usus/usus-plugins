package org.projectusus.core.statistics;

import static org.projectusus.core.internal.util.CoreTexts.codeProportionUnit_CLASS_label;

import java.util.List;

import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.basis.CodeStatistic;
import org.projectusus.core.basis.Hotspot;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;

public class ClassCountVisitor extends DefaultMetricsResultVisitor implements CodeStatisticCalculator {

    private int classCount = 0;

    public ClassCountVisitor( JavaModelPath path ) {
        super( codeProportionUnit_CLASS_label, path );
    }

    public ClassCountVisitor() {
        super( codeProportionUnit_CLASS_label );
    }

    @Override
    public void inspectClass( @SuppressWarnings( "unused" ) SourceCodeLocation location, @SuppressWarnings( "unused" ) MetricsResults result ) {
        classCount++;
    }

    public int getClassCount() {
        return classCount;
    }

    public CodeStatistic getCodeStatistic() {
        return new CodeStatistic( codeProportionUnit_CLASS_label, getClassCount() );
    }

    public ClassCountVisitor visitAndReturn() {
        visit();
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
