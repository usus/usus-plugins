package org.projectusus.ui.internal.hotspots;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class HotspotsCP implements IStructuredContentProvider {

    public Object[] getElements( Object inputElement ) {
        return new Object[] { inputElement.toString() };
    }

    public void dispose() {
        // unused
    }

    public void inputChanged( Viewer viewer, Object oldInput, Object newInput ) {
        // unused
    }
}
