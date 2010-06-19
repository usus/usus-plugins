package org.projectusus.core.internal.proportions.rawdata;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;

public class MethodVisitor extends DefaultMetricsResultVisitor {
    private int ccValueCount = 0;
    private int mlValueCount = 0;

    public MethodVisitor( IMethod method ) throws JavaModelException {
        super( new JavaModelPath( method ) );
        visit();
    }

    @Override
    public void inspect( @SuppressWarnings( "unused" ) SourceCodeLocation location, MethodRawData methodRawData ) {
        ccValueCount = ccValueCount + methodRawData.getCCValue();
        mlValueCount = mlValueCount + methodRawData.getMLValue();
    }

    public int getCCValue() {
        return ccValueCount;
    }

    public int getMLValue() {
        return mlValueCount;
    }

}
