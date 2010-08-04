package org.projectusus.ui.internal.hotspots;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;

public class DelegatingSelectionProvider implements ISelectionProvider, ISelectionChangedListener {

    private final Set<ISelectionChangedListener> listeners = new LinkedHashSet<ISelectionChangedListener>();
    private ISelectionProvider delegate = new NullSelectionProvider();

    public void addSelectionChangedListener( ISelectionChangedListener listener ) {
        listeners.add( listener );
    }

    public void removeSelectionChangedListener( ISelectionChangedListener listener ) {
        listeners.remove( listener );
    }

    public void switchTo( ISelectionProvider newDelegate ) {
        delegate.removeSelectionChangedListener( this );
        delegate = newDelegate;
        delegate.addSelectionChangedListener( this );
        fireSelectionChanged();
    }

    public ISelection getSelection() {
        return delegate.getSelection();
    }

    public void setSelection( ISelection selection ) {
        delegate.setSelection( selection );
    }

    private void fireSelectionChanged() {
        fireSelectionChanged( getSelection() );
    }

    protected void fireSelectionChanged( ISelection selection ) {
        SelectionChangedEvent event = new SelectionChangedEvent( this, selection );
        for( ISelectionChangedListener listener : listeners ) {
            listener.selectionChanged( event );
        }
    }

    public void selectionChanged( SelectionChangedEvent event ) {
        fireSelectionChanged( event.getSelection() );
    }
}
