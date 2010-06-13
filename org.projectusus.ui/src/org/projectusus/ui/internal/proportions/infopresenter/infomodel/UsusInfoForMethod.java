package org.projectusus.ui.internal.proportions.infopresenter.infomodel;

import static org.projectusus.core.basis.CodeProportionKind.CC;
import static org.projectusus.core.basis.CodeProportionKind.ML;

import java.util.List;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.projectusus.core.internal.proportions.rawdata.MethodVisitor;

public class UsusInfoForMethod extends UsusInfoForClass {

    private final IMethod method;

    UsusInfoForMethod( IMethod method ) throws JavaModelException {
        super( method.getDeclaringType() );
        this.method = method;
    }

    @Override
    protected void addFormattedProportion( List<String> result ) throws JavaModelException {
        super.addFormattedProportion( result );
        MethodVisitor visitor = new MethodVisitor( method );
        result.add( UsusModelElementFormatter.format( CC, visitor.getCCValue() ) );
        result.add( UsusModelElementFormatter.format( ML, visitor.getMLValue() ) );
    }

    @Override
    public String formatTitle() {
        return new MethodFormatter( method ).formatHeadInfo();
    }
}
