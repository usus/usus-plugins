package org.projectusus.ui.internal.proportions.infopresenter.infomodel;

import java.util.List;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.projectusus.core.statistics.visitors.MethodVisitor;

public class UsusInfoForMethod extends UsusInfoForClass {

    private final IMethod method;

    UsusInfoForMethod( IMethod method ) throws JavaModelException {
        super( method.getDeclaringType() );
        this.method = method;
    }

    @Override
    protected void addFormattedProportion( List<String> result ) throws JavaModelException {
        super.addFormattedProportion( result );
        MethodVisitor visitor = new MethodVisitor( method ).visitAndReturn();
        result.add( UsusModelElementFormatter.format( "Cyclomatic complexity", visitor.getCCValue() ) );
        result.add( UsusModelElementFormatter.format( "Method length", visitor.getMLValue() ) );
    }

    @Override
    public String formatTitle() {
        return new MethodFormatter( method ).formatHeadInfo();
    }
}
