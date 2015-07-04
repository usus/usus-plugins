package org.projectusus.ui.dependencygraph.common;

import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.ui.IContextMenuConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jdt.ui.actions.ConvertingSelectionProvider;
import org.eclipse.jdt.ui.actions.RefactorActionGroup;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
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
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.part.IShowInTarget;
import org.eclipse.ui.part.ShowInContext;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.zest.core.viewers.AbstractZoomableViewer;
import org.eclipse.zest.core.viewers.IZoomableWorkbenchPart;
import org.projectusus.core.IUsusModelListener;
import org.projectusus.core.statistics.UsusModelProvider;
import org.projectusus.jfeet.selection.ElementFrom;
import org.projectusus.ui.dependencygraph.filters.DirectNeighboursFilter;
import org.projectusus.ui.dependencygraph.filters.HideNodesFilter;
import org.projectusus.ui.dependencygraph.filters.IRestrictNodesFilterProvider;
import org.projectusus.ui.dependencygraph.filters.LimitNodeFilter;
import org.projectusus.ui.dependencygraph.filters.NodeAndEdgeFilter;
import org.projectusus.ui.dependencygraph.filters.PackagenameNodeFilter;
import org.projectusus.ui.dependencygraph.nodes.GraphNode;
import org.projectusus.ui.dependencygraph.nodes.IEdgeColorProvider;

public abstract class DependencyGraphView extends ViewPart implements IRestrictNodesFilterProvider, IShowInTarget, IZoomableWorkbenchPart, IMenuListener, IRefreshable {

    private final DependencyGraphModel model;
    private DependencyGraphViewer graphViewer;
    private IUsusModelListener listener;
    private NodeAndEdgeFilter customFilter; // selection of package, edge, package cycle, etc.
    private final WorkbenchContext customFilterContext; // this is connected to the eraser icon (activates and deactivates it)
    private DependencyGraphSelectionListener selectionListener;
    private final HideNodesFilter hideNodesFilter = new HideNodesFilter(); // the nodes the user manually X-ed out
    private boolean restricting = false;
    private final IPartListener2 selectionSynchronizationListener = new SelectionSynchronizationListener( this );
    private RefactorActionGroup refactorAction;
    private final IEdgeColorProvider edgeColorProvider;

    public DependencyGraphView( String viewId, DependencyGraphModel model, IEdgeColorProvider edgeColorProvider ) {
        this.model = model;
        this.edgeColorProvider = edgeColorProvider;
        customFilterContext = new WorkbenchContext( viewId + ".context.customFilter" );
        initUsusModelListener();
    }

    @Override
    public void createPartControl( Composite parent ) {
        Composite composite = createComposite( parent );
        createFilterArea( composite );
        createGraphViewer( new DependencyGraphViewer( createGraphArea( composite ), edgeColorProvider ) );

        IViewSite site = getViewSite();
        site.setSelectionProvider( graphViewer );
        contributeTo( site.getActionBars() );
        refresh();
        extendSelectionBehavior();
        registerContextMenu( site );
        refactorAction = new RefactorActionGroup( getSite(), new ConvertingSelectionProvider( site.getSelectionProvider() ) );
    }

    private void registerContextMenu( IViewSite site ) {
        MenuManager menuManager = new MenuManager();
        menuManager.add( new Separator( "nodeActions" ) );
        graphViewer.getGraphControl().setMenu( menuManager.createContextMenu( graphViewer.getControl() ) );
        site.registerContextMenu( menuManager, graphViewer );

        createRefactoringMenu( menuManager );
    }

    private void createRefactoringMenu( MenuManager menuManager ) {
        menuManager.add( new Separator( IContextMenuConstants.GROUP_REORGANIZE ) );
        menuManager.addMenuListener( this );
    }

    private static Composite createComposite( Composite parent ) {
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

    public void menuAboutToShow( IMenuManager manager ) {
        if( manager.find( RefactorActionGroup.MENU_ID ) == null ) {
            refactorAction.fillContextMenu( manager );
        }
    }

    public AbstractZoomableViewer getZoomableViewer() {
        return graphViewer;
    }

    private Composite createFilterArea( Composite composite ) {
        Composite filterArea = new Composite( composite, SWT.NONE );

        GridLayout layout = new GridLayout( 5, false );
        layout.marginHeight = 0;
        layout.marginTop = 5;
        filterArea.setLayout( layout );

        Control additionalWidgets = createAdditionalWidgets( filterArea );
        createLayoutComboViewer( filterArea );

        additionalWidgets.setLayoutData( new GridData( SWT.LEFT, SWT.CENTER, true, true ) );
        filterArea.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, false, false ) );

        return filterArea;
    }

    private void createLayoutComboViewer( Composite filterArea ) {
        new GraphLayoutComboViewer( filterArea, new ISelectionChangedListener() {
            public void selectionChanged( final SelectionChangedEvent event ) {
                Display.getDefault().asyncExec( new Runnable() {
                    public void run() {
                        GraphLayout newLayout = new ElementFrom( event.getSelection() ).as( GraphLayout.class );
                        switchLayout( newLayout );
                    }
                } );
            }
        } );
    }

    protected Control createAdditionalWidgets( Composite filterArea ) {
        return createRestrictingCheckBox( filterArea );
    }

    protected final Button createRestrictingCheckBox( Composite parent ) {
        final Button checkbox = new Button( parent, SWT.CHECK );
        checkbox.setText( getRestrictingCheckboxLabelName() );
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
        return checkbox;
    }

    private static Composite createGraphArea( Composite composite ) {
        Composite graphArea = new Composite( composite, SWT.BORDER );
        graphArea.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
        graphArea.setLayout( new FillLayout() );
        return graphArea;
    }

    private void createGraphViewer( DependencyGraphViewer dependencyGraphViewer ) {
        graphViewer = dependencyGraphViewer;
        List<ViewerFilter> filters = new ArrayList<ViewerFilter>();
        filters.add( new LimitNodeFilter( this ) );
        filters.add( hideNodesFilter );
        filters.addAll( createAdditionalFilters() );
        graphViewer.setFilters( filters.toArray( new ViewerFilter[filters.size()] ) );
    }

    protected Collection<? extends ViewerFilter> createAdditionalFilters() {
        return emptyList();
    }

    // hier kommt man an, wenn man in den Hotspots einen Package Cycle doppelklickt:
    public synchronized void replaceCustomFilter( NodeAndEdgeFilter newCustomFilter ) {
        newCustomFilter.setFilterLimitProvider( this );
        graphViewer.replaceFilter( customFilter, newCustomFilter );
        customFilter = newCustomFilter;

        setContentDescription( newCustomFilter.getDescription() );
        customFilterContext.activate();
        drawGraphUnconditionally();
    }

    public synchronized void clearCustomFilter() {
        if( customFilter != null ) {
            graphViewer.removeFilter( customFilter );
            customFilter = null;
            clearContentDescription();
        }
        customFilterContext.deactivate();
    }

    private void clearContentDescription() {
        setContentDescription( "" );
    }

    public void refresh() {
        Display.getDefault().asyncExec( new Runnable() {
            public void run() {
                drawGraphConditionally();
                applyLayout();
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
        registerEditorSynchronizationListener( false );
        if( refactorAction != null ) {
            refactorAction.dispose();
        }
        super.dispose();
    }

    public void registerEditorSynchronizationListener( boolean register ) {
        IWorkbenchPage page = getSite().getPage();
        if( register ) {
            page.addPartListener( selectionSynchronizationListener );
            selectNodeFromActiveEditor( page.getActiveEditor() );
        } else {
            page.removePartListener( selectionSynchronizationListener );
        }
    }

    private void initUsusModelListener() {
        listener = new IUsusModelListener() {
            public void ususModelChanged() {
                Display.getDefault().asyncExec( new Runnable() {
                    public void run() {
                        model.invalidate();
                        // TODO aOSD ConcurrentModificationException bei refresh
                        edgeColorProvider.refresh();
                        drawGraphConditionally();
                    }
                } );
            }
        };
        UsusModelProvider.ususModel().addUsusModelListener( listener );
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

    // adds the selected nodes to the list of hidden nodes
    public void hideSelectedNodes() {
        Set<GraphNode> selectedNodes = graphViewer.getSelectedNodes();
        if( !selectedNodes.isEmpty() ) {
            hideNodesFilter.addNodesToHide( selectedNodes );
            drawGraphConditionally();
        }
        customFilterContext.activate();
    }

    public void selectAllNodesInSamePackage( GraphNode selectedNode ) {
        // this only modifies the selection, so we do not adjust the neighboursFilter!
        Set<GraphNode> allNodes = graphViewer.getAllNodes();
        List<GraphNode> nodesInSamePackage = new LinkedList<GraphNode>();
        for( GraphNode node : allNodes ) {
            if( !node.isInDifferentPackageThan( selectedNode ) ) {
                nodesInSamePackage.add( node );
            }
        }
        graphViewer.selectNodes( nodesInSamePackage );
    }

    public void showAllDirectNeighbours() {
        replaceCustomFilter( new DirectNeighboursFilter( graphViewer.getSelectedNodes() ) );
        resetHiddenNodes(); // do this last because it redraws
    }

    private void switchLayout( final GraphLayout newLayout ) {
        graphViewer.setLayout( newLayout );
        drawGraphUnconditionally();
    }

    public void applyLayout() {
        graphViewer.applyLayout(); // redraw
    }

    // hides only those nodes that are passed to this method
    public void hideNodesAndRefresh( Set<GraphNode> hideNodes ) {
        hideNodesFilter.reset();
        hideNodesFilter.addNodesToHide( hideNodes );
        refresh();
        customFilterContext.activate();
    }

    public Set<GraphNode> getAllNodesToHide( Set<GraphNode> directNeighbours ) {
        HashSet<GraphNode> hideNodes = new HashSet<GraphNode>( model.getGraphNodes() );
        hideNodes.removeAll( directNeighbours );
        return hideNodes;
    }

    void selectNodeFromActiveEditor( IEditorPart editorPart ) {
        IJavaElement javaElement = JavaUI.getEditorInputJavaElement( editorPart.getEditorInput() );

        List<GraphNode> nodesToSelect = new ArrayList<GraphNode>();
        for( GraphNode node : graphViewer.getAllNodes() ) {
            if( node.represents( javaElement ) ) {
                nodesToSelect.add( node );
            }
        }
        graphViewer.selectNodes( nodesToSelect );
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

    private void setRestricting( boolean restricting ) {
        this.restricting = restricting;
    }

    protected abstract String getRestrictingCheckboxLabelName();

}
