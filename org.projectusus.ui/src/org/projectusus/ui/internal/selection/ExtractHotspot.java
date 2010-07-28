// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.selection;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.projectusus.ui.internal.DisplayHotspot;

public class ExtractHotspot {
    private final ISelection selection;

    public ExtractHotspot( ISelection selection ) {
        this.selection = selection;
    }

    public DisplayHotspot compute() {
        DisplayHotspot result = null;
        if( !selection.isEmpty() && selection instanceof IStructuredSelection ) {
            Object element = ((IStructuredSelection)selection).getFirstElement();
            if( element instanceof DisplayHotspot ) {
                result = (DisplayHotspot)element;
            }
        }
        return result;
    }
}
