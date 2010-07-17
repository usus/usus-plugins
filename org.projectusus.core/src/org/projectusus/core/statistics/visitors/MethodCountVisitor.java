package org.projectusus.core.statistics.visitors;

import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultMetricsResultVisitor;

public class MethodCountVisitor extends DefaultMetricsResultVisitor {

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

    public MethodCountVisitor visitAndReturn() {
        super.visit();
        return this;
    }
}
