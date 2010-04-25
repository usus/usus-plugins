// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.cockpit;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.projectusus.core.internal.proportions.IUsusModel;
import org.projectusus.core.internal.proportions.model.IUsusElement;

public class CockpitCP implements ITreeContentProvider {

    public Object[] getElements( Object inputElement ) {
        if( inputElement instanceof IUsusModel ) {
            return ((IUsusModel)inputElement).getElements();
        }
        return new Object[] {};
    }

    public Object[] getChildren( Object parentElement ) {
        Object[] result = new Object[0];
        if( parentElement instanceof IUsusElement ) {
            IUsusElement ususElement = (IUsusElement)parentElement;
            result = ususElement.getEntries().toArray();
        }
        return result;
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
