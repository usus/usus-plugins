package org.projectusus.core.statistics;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;

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

    @Override
    public MethodVisitor visit() {
        super.visit();
        return this;
    }
}
