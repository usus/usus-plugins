// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.hotspots.pages;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.projectusus.core.internal.proportions.model.CodeProportion;

class HotspotsCP implements ITreeContentProvider {

    public Object[] getElements( Object inputElement ) {
        Object[] result = new Object[0];
        if( inputElement instanceof CodeProportion ) {
            CodeProportion codeProportion = (CodeProportion)inputElement;
            result = codeProportion.getHotspots().toArray();
        }
        return result;
    }

    public void dispose() {
        // unused
    }

    public void inputChanged( Viewer viewer, Object oldInput, Object newInput ) {
        // unused
    }

    public Object[] getChildren( Object parentElement ) {
        return null;
    }

    public Object getParent( Object element ) {
        return null;
    }

    public boolean hasChildren( Object element ) {
        return getChildren( element ) != null && getChildren( element ).length > 0;
    }
}
