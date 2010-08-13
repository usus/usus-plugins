package org.projectusus.ui.dependencygraph.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.viewers.internal.ZoomManager;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphItem;
import org.projectusus.core.basis.GraphNode;

public class DependencyGraphViewer extends GraphViewer {

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

    public Image takeScreenshot() {
        ZoomManager zoomManager = getZoomManager();
        zoomManager.setZoom( zoomManager.getMaxZoom() );
        Image image = captureScreenshot();
        zoomManager.setZoomAsText( "100%" );
        return image;
    }

    private Image captureScreenshot() {
        Graph graph = (Graph)getControl();

        IFigure contents = graph.getContents();
        Image image = new Image( null, contents.getSize().width, contents.getSize().height );
        GC gc = new GC( image );
        SWTGraphics swtGraphics = new SWTGraphics( gc );

        Rectangle bounds = contents.getBounds();
        Point viewLocation = graph.getViewport().getViewLocation();
        swtGraphics.translate( -1 * bounds.x + viewLocation.x, -1 * bounds.y + viewLocation.y );

        graph.getViewport().paint( swtGraphics );
        gc.copyArea( image, 0, 0 );
        gc.dispose();
        return image;
    }
}
