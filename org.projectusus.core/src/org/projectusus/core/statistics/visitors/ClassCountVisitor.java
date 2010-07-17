package org.projectusus.core.statistics.visitors;

import static org.projectusus.core.internal.util.CoreTexts.codeProportionUnit_CLASS_label;

import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.basis.CodeStatistic;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultMetricsResultVisitor;

public class ClassCountVisitor extends DefaultMetricsResultVisitor {

    private int classCount = 0;

    public ClassCountVisitor( JavaModelPath path ) {
        super( path );
    }

    public ClassCountVisitor() {
        super( );
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

    public CodeProportion getCodeProportion() {
        // TODO Auto-generated method stub
        return null;
    }
}
