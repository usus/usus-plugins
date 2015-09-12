package org.projectusus.ui.dependencygraph.common;

import static org.projectusus.ui.dependencygraph.common.GraphLayout.getDefault;

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
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphItem;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.ui.dependencygraph.nodes.GraphNode;
import org.projectusus.ui.dependencygraph.nodes.IEdgeColorProvider;
import org.projectusus.ui.dependencygraph.nodes.NodeContentProvider;
import org.projectusus.ui.dependencygraph.nodes.NodeLabelProvider;
import org.projectusus.ui.dependencygraph.nodes.PackageRepresenter;
import org.projectusus.ui.util.EditorOpener;

public class DependencyGraphViewer extends GraphViewer {

    private final List<ISelectionChangedListener> duplicatedSelectionChangedListeners = new ArrayList<ISelectionChangedListener>();
    private final NodeLabelProvider labelProvider;

    public DependencyGraphViewer( Composite composite, IEdgeColorProvider edgeColorProvider ) {
        super( composite, SWT.NONE );
        labelProvider = new NodeLabelProvider( edgeColorProvider );
        setLabelProvider( labelProvider );

        setConnectionStyle( ZestStyles.CONNECTIONS_DIRECTED );
        setContentProvider( new NodeContentProvider() );
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

    public void setLayout( GraphLayout layout ) {
        setLayoutAlgorithm( layout.createAlgorithm(), false );
    }

    public Set<Packagename> getVisibleNodes() {
        refresh(); // Ensure all filters are applied
        Object[] allVisibleNodes = getFilteredChildren( getInput() );
        Set<Packagename> packageNames = new HashSet<Packagename>();
        for( Object node : allVisibleNodes ) {
            if( node instanceof PackageRepresenter ) {
                packageNames.add( ((PackageRepresenter)node).getPackagename() );
            }
        }
        return packageNames;
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

    public void addFilterIfNotAlreadyPresent( ViewerFilter filter ) {
        replaceFilter( filter, filter );
    }

    public void replaceFilter( ViewerFilter existingFilter, ViewerFilter newFilter ) {
        List<ViewerFilter> filters = collectFiltersExcept( existingFilter );
        filters.add( newFilter );
        setFilters( filters.toArray( new ViewerFilter[filters.size()] ) );
    }

    private List<ViewerFilter> collectFiltersExcept( ViewerFilter existingFilter ) {
        List<ViewerFilter> filters = new ArrayList<ViewerFilter>();
        for( ViewerFilter viewerFilter : getFilters() ) {
            if( viewerFilter != existingFilter ) {
                filters.add( viewerFilter );
            }
        }
        return filters;
    }

    void selectNodes( List<GraphNode> nodesToSelect ) {
        setSelection( new StructuredSelection( nodesToSelect ) );
    }

    public void setHighlightStrongConnections( boolean highlightStrongConnections ) {
        labelProvider.setHighlightStrongConnections( highlightStrongConnections );
    }

}
