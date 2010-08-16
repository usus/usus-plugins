package org.projectusus.ui.dependencygraph.common;

import static java.util.Arrays.sort;

import java.util.Set;

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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.ui.part.IShowInTarget;
import org.eclipse.ui.part.ShowInContext;
import org.eclipse.ui.part.ViewPart;
import org.projectusus.core.IUsusModelListener;
import org.projectusus.core.UsusModelProvider;
import org.projectusus.core.basis.GraphNode;
import org.projectusus.ui.dependencygraph.filters.HideNodesFilter;
import org.projectusus.ui.dependencygraph.filters.IFilterLimitProvider;
import org.projectusus.ui.dependencygraph.filters.LimitNodeFilter;
import org.projectusus.ui.dependencygraph.filters.NodeFilter;
import org.projectusus.ui.dependencygraph.filters.PackagenameNodeFilter;

public abstract class DependencyGraphView extends ViewPart implements IFilterLimitProvider, IShowInTarget {

    private static final String LAYOUT_LABEL = "Layout:";
    private static final String SCALE_TOOLTIP_TEXT = "Change the number of visible nodes by moving the slider";

    private final DependencyGraphModel model;
    private DependencyGraphViewer graphViewer;
    private int filterLimit = -1;
    private IUsusModelListener listener;
    private Scale scale;
    private ViewerFilter customFilter;
    private final WorkbenchContext customFilterContext;
    private DependencyGraphSelectionListener selectionListener;
    private final HideNodesFilter hideNodesFilter = new HideNodesFilter();

    public DependencyGraphView( DependencyGraphModel model ) {
        super();
        this.model = model;
        customFilterContext = new WorkbenchContext( getClass().getName() + ".context.customFilter" );
        initUsusModelListener();
    }

    @Override
    public void createPartControl( Composite parent ) {
        Composite composite = new Composite( parent, SWT.NONE );
        GridLayout layout = new GridLayout( 1, false );
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        composite.setLayout( layout );
        Composite filterArea = createFilterArea( composite );
        GridData data = new GridData();
        data.horizontalAlignment = SWT.FILL;
        filterArea.setLayoutData( data );
        createGraphArea( composite );
        getViewSite().setSelectionProvider( graphViewer );
        refresh();
        extendSelectionBehavior();
    }

    private void initFilterLimit( int maxFilterValue ) {
        if( filterLimit == -1 ) {
            setFilterLimit( maxFilterValue );
            scale.setSelection( filterLimit );
        }
    }

    private Composite createFilterArea( Composite composite ) {
        Composite filterArea = new Composite( composite, SWT.NONE );
        filterArea.setToolTipText( SCALE_TOOLTIP_TEXT );

        GridLayout layout = new GridLayout( 5, false );
        layout.marginHeight = 0;
        layout.marginTop = 5;
        filterArea.setLayout( layout );

        createScale( filterArea );
        createLayoutCombo( filterArea );

        return filterArea;
    }

    private void createLayoutCombo( Composite filterArea ) {
        createLabel( filterArea, LAYOUT_LABEL );
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

    private void createScale( Composite filterArea ) {
        createLabel( filterArea, getScaleLeftLabelText() );

        scale = new Scale( filterArea, SWT.HORIZONTAL );
        scale.setMinimum( 0 );
        scale.setMaximum( 9999999 );
        scale.setSelection( getFilterLimit() );
        scale.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent e ) {
                Display.getDefault().asyncExec( new Runnable() {
                    public void run() {
                        int spinnerValue = scale.getSelection();
                        setFilterLimit( spinnerValue );
                        drawGraphConditionally();
                    }
                } );
            }
        } );

        Label filterText = createLabel( filterArea, getScaleRightLabelText() );
        GridData gridData = new GridData();
        gridData.grabExcessHorizontalSpace = true;
        filterText.setLayoutData( gridData );
    }

    protected abstract String getScaleRightLabelText();

    protected abstract String getScaleLeftLabelText();

    private Label createLabel( Composite parent, String labelText ) {
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

    public synchronized void setCustomFilter( NodeFilter customFilter ) {
        clearCustomFilter();
        this.customFilter = customFilter;
        graphViewer.addFilter( customFilter );
        setContentDescription( customFilter.getDescription() );
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

    private void drawGraphConditionally() {
        if( model.isChanged() ) {
            drawGraphUnconditionally();
        } else {
            graphViewer.refresh();
        }
        graphViewer.fireSelectionChanged();
    }

    private void drawGraphUnconditionally() {
        Set<? extends GraphNode> graphNodes = model.getGraphNodes();
        if( !graphNodes.isEmpty() ) {
            updateSpinnerAndFilter();
        }
        graphViewer.setInput( graphNodes );
        model.resetChanged();
    }

    @Override
    public void setFocus() {
        graphViewer.getControl().setFocus();
    }

    public int getFilterLimit() {
        return filterLimit;
    }

    private void setFilterLimit( int filterLimit ) {
        this.filterLimit = filterLimit;
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

    private void updateSpinnerAndFilter() {
        int maxFilterValue = model.getMaxFilterValue();
        initFilterLimit( maxFilterValue );
        scale.setMaximum( calcMaxFilterValue( maxFilterValue ) );
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
        setCustomFilter( filter );
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

    public void resetHiddenNodes() {
        hideNodesFilter.reset();
        drawGraphConditionally();
    }

    public Image takeScreenshot() {
        return graphViewer.takeScreenshot();
    }

    public abstract String getFilenameForScreenshot();
}
