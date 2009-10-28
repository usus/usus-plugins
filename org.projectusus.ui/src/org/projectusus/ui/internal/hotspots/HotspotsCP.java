// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.hotspots;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.projectusus.core.internal.proportions.model.CodeProportion;

public class HotspotsCP implements IStructuredContentProvider {

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
}
