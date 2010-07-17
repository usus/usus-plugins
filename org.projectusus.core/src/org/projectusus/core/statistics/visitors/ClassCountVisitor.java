package org.projectusus.core.statistics.visitors;

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
        super();
    }

    @Override
    public void inspectClass( @SuppressWarnings( "unused" ) SourceCodeLocation location, @SuppressWarnings( "unused" ) MetricsResults result ) {
        classCount++;
    }

    public int getClassCount() {
        return classCount;
    }

    public ClassCountVisitor visitAndReturn() {
        visit();
        return this;
    }
}
