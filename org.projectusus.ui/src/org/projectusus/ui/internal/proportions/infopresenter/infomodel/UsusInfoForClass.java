package org.projectusus.ui.internal.proportions.infopresenter.infomodel;

import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.KG;

import java.util.List;

import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.projectusus.core.internal.UsusCorePlugin;

public class UsusInfoForClass extends UsusInfoForFile {

    private final IType clazz;

    UsusInfoForClass( IType clazz ) throws JavaModelException {
        super( clazz.getUnderlyingResource() );
        this.clazz = clazz;
    }

    @Override
    protected void addFormattedProportion( List<String> result ) throws JavaModelException {
        super.addFormattedProportion( result );
        UsusModelElementFormatter formatter = new UsusModelElementFormatter();
        result.add( formatter.format( KG, UsusCorePlugin.getUsusModel().getNumberOfMethods( clazz ) ) );
    }

    @Override
    public String formatTitle() {
        return clazz.getElementName();
    }
}
