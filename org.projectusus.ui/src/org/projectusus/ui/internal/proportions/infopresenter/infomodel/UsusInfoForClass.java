package org.projectusus.ui.internal.proportions.infopresenter.infomodel;

import static org.projectusus.core.basis.CodeProportionKind.KG;

import java.util.List;

import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.projectusus.core.internal.proportions.rawdata.CCDSumVisitor;
import org.projectusus.core.internal.proportions.rawdata.MethodCountVisitor;
import org.projectusus.core.internal.proportions.rawdata.JavaModelPath;

public class UsusInfoForClass extends UsusInfoForFile {

    private final JavaModelPath path;

    UsusInfoForClass( IType clazz ) throws JavaModelException {
        super( clazz.getUnderlyingResource() );
        path = new JavaModelPath( clazz );
    }

    @Override
    protected void addFormattedProportion( List<String> result ) throws JavaModelException {
        super.addFormattedProportion( result );
        result.add( UsusModelElementFormatter.format( KG, new MethodCountVisitor( path ).getMethodCount() ) );
        result.add( UsusModelElementFormatter.format( "Cumulative Component Dependency (of class)", new CCDSumVisitor( path ).getCCDSum() ) );
    }

    @Override
    public String formatTitle() {
        return path.getType().getElementName();
    }
}
