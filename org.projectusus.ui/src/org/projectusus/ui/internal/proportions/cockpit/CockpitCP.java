// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.cockpit;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class CockpitCP implements ITreeContentProvider {

    public Object[] getElements( Object inputElement ) {
        if( inputElement instanceof CockpitModel ) {
            return ((CockpitModel)inputElement).getCategories();
        }
        return new Object[] {};
    }

    public Object[] getChildren( Object parentElement ) {
        if( parentElement instanceof CockpitCategory ) {
            CockpitCategory category = (CockpitCategory)parentElement;
            return category.getChildren();
        }
        return new Object[0];
    }

    public Object getParent( Object element ) {
        return null;
    }

    public boolean hasChildren( Object element ) {
        return getChildren( element ).length > 0;
    }

    public void dispose() {
        // unused
    }

    public void inputChanged( Viewer viewer, Object oldInput, Object newInput ) {
        // unused
    }
}
