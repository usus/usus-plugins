package org.projectusus.core.statistics.visitors;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultMetricsResultVisitor;

public class MethodVisitor extends DefaultMetricsResultVisitor {
    private int ccValueCount = 0;
    private int mlValueCount = 0;

    public MethodVisitor( IMethod method ) throws JavaModelException {
        super( new JavaModelPath( method ) );
    }

    @Override
    public void inspectMethod( @SuppressWarnings( "unused" ) SourceCodeLocation location, MetricsResults result ) {
        ccValueCount = ccValueCount + result.getIntValue( MetricsResults.CC, 1 );
        mlValueCount = mlValueCount + result.getIntValue( MetricsResults.ML, 1 );
    }

    public int getCCValue() {
        return ccValueCount;
    }

    public int getMLValue() {
        return mlValueCount;
    }

    public MethodVisitor visitAndReturn() {
        super.visit();
        return this;
    }
}
