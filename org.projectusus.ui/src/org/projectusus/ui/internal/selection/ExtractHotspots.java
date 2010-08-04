// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.selection;

import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.projectusus.ui.internal.DisplayHotspot;

public class ExtractHotspots {
    private final ISelection selection;

    public ExtractHotspots( ISelection selection ) {
        this.selection = selection;
    }

    @SuppressWarnings( "unchecked" )
    public List<DisplayHotspot<?>> compute() {
        if( !selection.isEmpty() && selection instanceof IStructuredSelection ) {
            return ((IStructuredSelection)selection).toList();
        }
        return null;
    }
}
