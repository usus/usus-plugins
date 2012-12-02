package org.projectusus.ui.dependencygraph.common;

import static org.projectusus.ui.dependencygraph.common.GraphLayouts.getDefault;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphItem;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.projectusus.ui.dependencygraph.nodes.GraphNode;
import org.projectusus.ui.util.EditorOpener;

public class DependencyGraphViewer extends GraphViewer {

    private final List<ISelectionChangedListener> duplicatedSelectionChangedListeners = new ArrayList<ISelectionChangedListener>();
    private final NodeLabelProvider labelProvider = new NodeLabelProvider();

    public DependencyGraphViewer( Composite composite ) {
        super( composite, SWT.NONE );
        setConnectionStyle( ZestStyles.CONNECTIONS_DIRECTED );
        setContentProvider( new NodeContentProvider() );
        setLabelProvider( labelProvider );
        addDoubleClickListener( new IDoubleClickListener() {
            public void doubleClick( DoubleClickEvent event ) {
                ISelection selection = getSelection();
                Object selected = ((IStructuredSelection)selection).getFirstElement();
                if( selected instanceof GraphNode ) {
                    GraphNode graphNode = (GraphNode)selected;
                    EditorOpener opener = new EditorOpener();
                    opener.openEditor( graphNode.getFile() );
                }
            }
        } );
        setLayout( getDefault() );
    }

    public void setLayout( GraphLayouts layout ) {
        setLayoutAlgorithm( layout.createAlgorithm(), false );
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

    @SuppressWarnings( "unchecked" )
    public Set<GraphNode> getSelectedNodes() {
        return convert( getGraphControl().getSelection() );
    }

    @SuppressWarnings( "unchecked" )
    public Set<GraphNode> getAllNodes() {
        return convert( getGraphControl().getNodes() );
    }

    private Set<GraphNode> convert( List<GraphItem> selection ) {
        Set<GraphNode> result = new HashSet<GraphNode>();
        for( GraphItem graphItem : selection ) {
            if( graphItem instanceof org.eclipse.zest.core.widgets.GraphNode ) {
                result.add( (GraphNode)graphItem.getData() );
            }
        }
        return result;
    }

    public Image takeScreenshot() {
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
