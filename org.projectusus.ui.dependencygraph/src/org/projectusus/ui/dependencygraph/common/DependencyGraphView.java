package org.projectusus.ui.dependencygraph.common;

import static java.util.Arrays.sort;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.part.IShowInTarget;
import org.eclipse.ui.part.ShowInContext;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.zest.core.viewers.AbstractZoomableViewer;
import org.eclipse.zest.core.viewers.IZoomableWorkbenchPart;
import org.projectusus.core.IUsusModelListener;
import org.projectusus.core.statistics.UsusModelProvider;
import org.projectusus.ui.dependencygraph.filters.HideNodesFilter;
import org.projectusus.ui.dependencygraph.filters.IRestrictNodesFilterProvider;
import org.projectusus.ui.dependencygraph.filters.LimitNodeFilter;
import org.projectusus.ui.dependencygraph.filters.NodeAndEdgeFilter;
import org.projectusus.ui.dependencygraph.filters.PackagenameNodeFilter;
import org.projectusus.ui.dependencygraph.handlers.ChangeZoom;
import org.projectusus.ui.dependencygraph.nodes.GraphNode;

public abstract class DependencyGraphView extends ViewPart implements IRestrictNodesFilterProvider, IShowInTarget, IZoomableWorkbenchPart {

    private static final String LAYOUT_LABEL = "Layout:";
    private static final String SCALE_TOOLTIP_TEXT = "Change the number of visible nodes by moving the slider";

    private final DependencyGraphModel model;
    private DependencyGraphViewer graphViewer;
    private IUsusModelListener listener;
    private NodeAndEdgeFilter customFilter;
    private final WorkbenchContext customFilterContext;
    private DependencyGraphSelectionListener selectionListener;
    private final HideNodesFilter hideNodesFilter = new HideNodesFilter();
    private boolean restricting = false;

    public DependencyGraphView( DependencyGraphModel model ) {
        super();
        this.model = model;
        customFilterContext = new WorkbenchContext( getClass().getName() + ".context.customFilter" );
        initUsusModelListener();
    }

    @Override
    public void createPartControl( Composite parent ) {
        Composite composite = createComposite( parent );
        createFilterArea( composite );
        createGraphArea( composite );
        IViewSite site = getViewSite();
        site.setSelectionProvider( graphViewer );
        contributeTo( site.getActionBars() );
        refresh();
        extendSelectionBehavior();
        registerContextMenu( site );
    }

    private void registerContextMenu( IViewSite site ) {
        MenuManager menuManager = new MenuManager();
        menuManager.add( new Separator( "nodeActions" ) );
        graphViewer.getGraphControl().setMenu( menuManager.createContextMenu( graphViewer.getControl() ) );
        site.registerContextMenu( menuManager, graphViewer );
    }

    protected Composite createComposite( Composite parent ) {
        Composite composite = new Composite( parent, SWT.NONE );
        GridLayout layout = new GridLayout( 1, false );
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        composite.setLayout( layout );
        return composite;
    }

    protected void contributeTo( IActionBars actionBars ) {
        IToolBarManager toolBar = actionBars.getToolBarManager();
        Separator separator = new Separator( "zoom" );
        separator.setVisible( false );
        toolBar.add( separator );
        toolBar.add( new ChangeZoom( this ) );
    }

    public AbstractZoomableViewer getZoomableViewer() {
        return graphViewer;
    }

    private Composite createFilterArea( Composite composite ) {
        Composite filterArea = new Composite( composite, SWT.NONE );
        filterArea.setToolTipText( SCALE_TOOLTIP_TEXT );

        GridLayout layout = new GridLayout( 5, false );
        layout.marginHeight = 0;
        layout.marginTop = 5;
        filterArea.setLayout( layout );

        createAdditionalWidget( filterArea );
        createLayoutCombo( filterArea );

        filterArea.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, false, false ) );

        return filterArea;
    }

    private void createLayoutCombo( Composite filterArea ) {
        Label label = createLabel( filterArea, LAYOUT_LABEL );
        label.setLayoutData( new GridData( SWT.RIGHT, SWT.CENTER, true, false ) );
        ComboViewer comboViewer = new ComboViewer( filterArea, SWT.READ_ONLY );
        comboViewer.setContentProvider( new ArrayContentProvider() );
        comboViewer.setLabelProvider( new LabelProvider() );
        GraphLayouts[] layouts = GraphLayouts.values();
        sort( layouts, new GraphLayoutsComparator() );
        comboViewer.setInput( layouts );
        comboViewer.setSelection( new StructuredSelection( GraphLayouts.getDefault() ) );
        comboViewer.addSelectionChangedListener( new ISelectionChangedListener() {
            public void selectionChanged( final SelectionChangedEvent event ) {
                Display.getDefault().asyncExec( new Runnable() {
                    public void run() {
                        IStructuredSelection selection = (IStructuredSelection)event.getSelection();
                        graphViewer.setLayout( (GraphLayouts)selection.getFirstElement() );
                        drawGraphUnconditionally();
                    }
                } );
            }
        } );
    }

    protected void createAdditionalWidget( Composite filterArea ) {
        final Button checkbox = new Button( filterArea, SWT.CHECK );
        checkbox.setText( getCheckboxLabelName() );
        checkbox.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent e ) {
                Display.getDefault().asyncExec( new Runnable() {
                    public void run() {
                        setRestricting( checkbox.getSelection() );
                        drawGraphConditionally();
                        refresh();
                    }
                } );
            }
        } );
        setRestricting( false );
    }

    protected Label createLabel( Composite parent, String labelText ) {
        Label label = new Label( parent, SWT.NONE );
        label.setText( labelText );
        return label;
    }

    private Composite createGraphArea( Composite composite ) {
        Composite graphArea = new Composite( composite, SWT.BORDER );
        graphArea.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
        graphArea.setLayout( new FillLayout() );
        createGraphViewer( graphArea );
        return graphArea;
    }

    private void createGraphViewer( Composite graphArea ) {
        graphViewer = new DependencyGraphViewer( graphArea );
        graphViewer.setFilters( new ViewerFilter[] { new LimitNodeFilter( this ), hideNodesFilter } );
    }

    public synchronized void replaceCustomFilter( NodeAndEdgeFilter newCustomFilter ) {
        newCustomFilter.setFilterLimitProvider( this );
        graphViewer.replaceFilter( customFilter, newCustomFilter );
        customFilter = newCustomFilter;

        setContentDescription( newCustomFilter.getDescription() );
        customFilterContext.activate();
        drawGraphUnconditionally();
    }

    protected String getCustomFilterContextId() {
        return getClass().getName() + ".context.customFilter";
    }

    public synchronized void clearCustomFilter() {
        if( customFilter != null ) {
            graphViewer.removeFilter( customFilter );
            customFilter = null;
            setContentDescription( "" );
        }
        customFilterContext.deactivate();
    }

    public void refresh() {
        Display.getDefault().asyncExec( new Runnable() {
            public void run() {
                drawGraphConditionally();
                graphViewer.applyLayout();
            }
        } );
    }

    public void drawGraphConditionally() {
        if( model.isChanged() ) {
            drawGraphUnconditionally();
        } else {
            graphViewer.refresh();
        }
        graphViewer.fireSelectionChanged();
    }

    private void drawGraphUnconditionally() {
        graphViewer.setInput( model.getGraphNodes() );
        model.resetChanged();
    }

    @Override
    public void setFocus() {
        graphViewer.getControl().setFocus();
    }

    @Override
    public void dispose() {
        UsusModelProvider.ususModel().removeUsusModelListener( listener );
        graphViewer.getGraphControl().removeSelectionListener( selectionListener );
        super.dispose();
    }

    private void initUsusModelListener() {
        listener = new IUsusModelListener() {
            public void ususModelChanged() {
                Display.getDefault().asyncExec( new Runnable() {
                    public void run() {
                        model.invalidate();
                        drawGraphConditionally();
                    }
                } );
            }
        };
        UsusModelProvider.ususModel().addUsusModelListener( listener );
    }

    protected int calcMaxFilterValue( int maxFilterValue ) {
        return maxFilterValue;
    }

    private void extendSelectionBehavior() {
        selectionListener = new DependencyGraphSelectionListener( graphViewer );
        graphViewer.getGraphControl().addSelectionListener( selectionListener );
    }

    public boolean show( ShowInContext context ) {
        PackagenameNodeFilter filter = PackagenameNodeFilter.from( context.getSelection() );
        if( filter.isEmpty() ) {
            return false;
        }
        replaceCustomFilter( filter );
        return true;
    }

    public void hideSelectedNodes() {
        Set<GraphNode> selectedNodes = graphViewer.getSelectedNodes();
        if( !selectedNodes.isEmpty() ) {
            hideNodesFilter.addNodesToHide( selectedNodes );
            drawGraphConditionally();
        }
        customFilterContext.activate();
    }

    public void selectAllNodesInSamePackage( GraphNode selectedNode ) {
        Set<GraphNode> allNodes = graphViewer.getAllNodes();
        List<GraphNode> nodesInSamePackage = new LinkedList<GraphNode>();
        for( GraphNode node : allNodes ) {
            if( !node.isInDifferentPackageThan( selectedNode ) ) {
                nodesInSamePackage.add( node );
            }
        }
        graphViewer.setSelection( new StructuredSelection( nodesInSamePackage ) );
    }

    public void showAllDirectNeighbours() {
        Set<GraphNode> directNeighbours = new HashSet<GraphNode>();
        for( GraphNode selectedNode : graphViewer.getSelectedNodes() ) {
            directNeighbours.add( selectedNode );
            directNeighbours.addAll( selectedNode.getChildren() );
            directNeighbours.addAll( selectedNode.getParents() );
        }

        if( !directNeighbours.isEmpty() ) {
            hideNodesFilter.reset();
            // TODO this flickers :-( But multiple applications don't seem to work without drawing :-(
            drawGraphConditionally();

            HashSet<GraphNode> hideNodes = new HashSet<GraphNode>( graphViewer.getAllNodes() );
            hideNodes.removeAll( directNeighbours );
            hideNodesFilter.addNodesToHide( hideNodes );
            drawGraphConditionally();
        }
        customFilterContext.activate();
    }

    public void resetHiddenNodes() {
        hideNodesFilter.reset();
        drawGraphConditionally();
    }

    public Image takeScreenshot() {
        return graphViewer.takeScreenshot();
    }

    public abstract String getFilenameForScreenshot();

    public boolean isRestricting() {
        return restricting;
    }

    protected void setRestricting( boolean restricting ) {
        this.restricting = restricting;
    }

    protected abstract String getCheckboxLabelName();
}
