package org.projectusus.ui.internal.proportions.infopresenter.infomodel;

import java.util.List;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.projectusus.core.statistics.CyclomaticComplexityCountVisitor;
import org.projectusus.core.statistics.MethodLengthCountVisitor;
import org.projectusus.core.statistics.MethodVisitor;

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
        result.add( UsusModelElementFormatter.format( new CyclomaticComplexityCountVisitor().getLabel(), visitor.getCCValue() ) );
        result.add( UsusModelElementFormatter.format( new MethodLengthCountVisitor().getLabel(), visitor.getMLValue() ) );
    }

    @Override
    public String formatTitle() {
        return new MethodFormatter( method ).formatHeadInfo();
    }
}
