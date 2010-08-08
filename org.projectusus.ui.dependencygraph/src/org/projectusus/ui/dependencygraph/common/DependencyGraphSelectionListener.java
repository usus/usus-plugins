package org.projectusus.ui.dependencygraph.common;

import java.util.List;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphItem;

class DependencyGraphSelectionListener implements SelectionListener {

    public void widgetSelected( SelectionEvent event ) {
        handleOpposingEdge( event );
    }

    @SuppressWarnings( "unchecked" )
    private void handleOpposingEdge( SelectionEvent event ) {
        if( event.item instanceof GraphConnection ) {
            GraphConnection selectedConn = (GraphConnection)event.item;
            Graph graph = (Graph)event.getSource();
            List<GraphItem> selection = graph.getSelection();
            boolean adding = selection.contains( selectedConn );
            for( GraphConnection currentConn : (List<GraphConnection>)graph.getConnections() ) {
                if( areOpposing( selectedConn, currentConn ) ) {
                    if( adding && !selection.contains( currentConn ) ) {
                        selection.add( currentConn );
                        graph.setSelection( selection.toArray( new GraphItem[] {} ) );
                    } else if( !adding ) {
                        selection.remove( currentConn );
                        currentConn.unhighlight();
                        graph.setSelection( selection.toArray( new GraphItem[] {} ) );
                    }
                }
            }

        }
    }

    private boolean areOpposing( GraphConnection conn1, GraphConnection conn2 ) {
        return conn1.getSource().equals( conn2.getDestination() ) && conn1.getDestination().equals( conn2.getSource() );
    }

    public void widgetDefaultSelected( SelectionEvent e ) {
        // not needed
    }
}
