package org.projectusus.ui.internal.proportions.infopresenter.infomodel;

import java.util.List;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.projectusus.core.statistics.CyclomaticComplexityStatistic;
import org.projectusus.core.statistics.MethodLengthStatistic;
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
        MethodVisitor visitor = new MethodVisitor( method ).visit();
        result.add( UsusModelElementFormatter.format( new CyclomaticComplexityStatistic().getLabel(), visitor.getCCValue() ) );
        result.add( UsusModelElementFormatter.format( new MethodLengthStatistic().getLabel(), visitor.getMLValue() ) );
    }

    @Override
    public String formatTitle() {
        return new MethodFormatter( method ).formatHeadInfo();
    }
}
