package org.projectusus.ui.internal.proportions;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.projectusus.core.internal.proportions.model.CodeProportion;

class ExtractCodeProportion {

    private final ISelection selection;

    public ExtractCodeProportion( ISelection selection ) {
        this.selection = selection;
    }

    public CodeProportion compute() {
        CodeProportion result = null;
        if( selection instanceof IStructuredSelection && !selection.isEmpty() ) {
            IStructuredSelection ssel = (IStructuredSelection)selection;
            Object element = ssel.getFirstElement();
            if( element instanceof CodeProportion ) {
                result = (CodeProportion)element;
            }
        }
        return result;
    }

}
