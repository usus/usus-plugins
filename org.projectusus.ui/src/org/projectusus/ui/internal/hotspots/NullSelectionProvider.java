package org.projectusus.ui.internal.hotspots;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;

public class NullSelectionProvider implements ISelectionProvider {

    public void addSelectionChangedListener( @SuppressWarnings( "unused" ) ISelectionChangedListener listener ) {
        // do nothing
    }

    public void removeSelectionChangedListener( @SuppressWarnings( "unused" ) ISelectionChangedListener listener ) {
        // do nothing
    }

    public ISelection getSelection() {
        return StructuredSelection.EMPTY;
    }

    public void setSelection( @SuppressWarnings( "unused" ) ISelection selection ) {
        // do nothing
    }
}
