package org.projectusus.ui.dependencygraph.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.widgets.GraphItem;
import org.projectusus.core.basis.GraphNode;

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

    public Set<GraphNode> getSelectedNodes() {
        @SuppressWarnings( "unchecked" )
        List<GraphItem> selection = getGraphControl().getSelection();
        Set<GraphNode> result = new HashSet<GraphNode>();
        for( GraphItem graphItem : selection ) {
            if( graphItem instanceof org.eclipse.zest.core.widgets.GraphNode ) {
                result.add( (GraphNode)graphItem.getData() );
            }
        }
        return result;
    }
}
