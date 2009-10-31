// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.hotspots;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.projectusus.ui.internal.hotspots.pages.IHotspotsPage;

public class ExtractHotspotsPage {

    private final ISelection selection;

    public ExtractHotspotsPage( ISelection selection ) {
        this.selection = selection;
    }

    public IHotspotsPage compute() {
        IHotspotsPage result = null;
        if( !selection.isEmpty() && selection instanceof IStructuredSelection ) {
            IStructuredSelection ssel = (IStructuredSelection)selection;
            result = extractPageAdapter( ssel.getFirstElement() );
        }
        return result;
    }

    private IHotspotsPage extractPageAdapter( Object element ) {
        IHotspotsPage result = null;
        if( element instanceof IAdaptable ) {
            Object adapter = ((IAdaptable)element).getAdapter( IHotspotsPage.class );
            if( adapter instanceof IHotspotsPage ) {
                result = (IHotspotsPage)adapter;
            }
        }
        return result;
    }
}
