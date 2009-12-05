// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
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
