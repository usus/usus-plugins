package org.projectusus.ui.dependencygraph.common;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.zest.core.viewers.GraphViewer;

class DependencyGraphViewer extends GraphViewer {

    private final List<ISelectionChangedListener> duplicatedSelectionChangedListeners = new ArrayList<ISelectionChangedListener>();

    public DependencyGraphViewer( Composite composite, int style ) {
        super( composite, style );
    }

    @Override
    public void addSelectionChangedListener( ISelectionChangedListener listener ) {
        super.addSelectionChangedListener( listener );
        duplicatedSelectionChangedListeners.add( listener );
    }

    @Override
    public void removeSelectionChangedListener( ISelectionChangedListener listener ) {
        super.removeSelectionChangedListener( listener );
        duplicatedSelectionChangedListeners.remove( listener );
    }

    void fireSelectionChanged() {
        ISelection structuredSelection = getSelection();
        SelectionChangedEvent event = new SelectionChangedEvent( DependencyGraphViewer.this, structuredSelection );
        for( ISelectionChangedListener listener : duplicatedSelectionChangedListeners ) {
            listener.selectionChanged( event );
        }
    }

}
