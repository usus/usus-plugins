package org.projectusus.ui.dependencygraph.common;

import static org.projectusus.core.internal.UsusCorePlugin.getUsusModel;

import java.util.Set;

import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.projectusus.core.internal.proportions.IUsusModelListener;
import org.projectusus.core.internal.proportions.rawdata.GraphNode;

public abstract class DependencyGraphView extends ViewPart implements FilterLimitProvider {
    private static final String SCALE_TOOLTIP_TEXT = "Change the number of visible nodes by moving the slider";

    private GraphViewer graphViewer;
    private int filterLimit = -1;
    private final DependencyGraphModel model;
    private IUsusModelListener listener;
    private Scale scale;

    public DependencyGraphView( DependencyGraphModel model ) {
        super();
        this.model = model;
        initUsusModelListener();
    }

    @Override
    public void createPartControl( Composite parent ) {
        Composite composite = new Composite( parent, SWT.NONE );
        composite.setLayout( new GridLayout( 1, false ) );
        createFilterArea( composite );
        createGraphArea( composite );
    }

    private void initFilterLimit( int maxFilterValue ) {
        if( filterLimit == -1 ) {
            setFilterLimit( maxFilterValue );
            scale.setSelection( filterLimit );
        }
    }

    private void createFilterArea( Composite composite ) {
        Composite filterArea = new Composite( composite, SWT.BORDER );
        filterArea.setToolTipText( SCALE_TOOLTIP_TEXT );
        filterArea.setLayout( new GridLayout( 3, false ) );
        createLabel( filterArea, getScaleLeftLabelText() );

        scale = new Scale( filterArea, SWT.HORIZONTAL );
        scale.setMinimum( 0 );
        scale.setMaximum( 9999999 );
        scale.setSelection( getFilterLimit() );
        scale.addSelectionListener( new SelectionListener() {
            public void widgetDefaultSelected( SelectionEvent e ) {
                // we don't need that
            }

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
        createLabel( filterArea, getScaleRightLabelText() );
    }

    protected abstract String getScaleRightLabelText();

    protected abstract String getScaleLeftLabelText();

    private void createLabel( Composite parent, String labelText ) {
        Label filterText = new Label( parent, SWT.NONE );
        filterText.setText( labelText );
    }

    private void createGraphArea( Composite composite ) {
        Composite graphArea = new Composite( composite, SWT.BORDER );
        graphArea.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
        graphArea.setLayout( new FillLayout() );
        createGraphViewer( graphArea );
        refresh();
    }

    private void createGraphViewer( Composite graphArea ) {
        graphViewer = new GraphViewer( graphArea, SWT.NONE );
        graphViewer.setConnectionStyle( ZestStyles.CONNECTIONS_DIRECTED );
        graphViewer.setContentProvider( new NodeContentProvider() );
        graphViewer.setLabelProvider( new NodeLabelProvider() );
        SpringLayoutAlgorithm layoutAlgorithm = new SpringLayoutAlgorithm( LayoutStyles.NO_LAYOUT_NODE_RESIZING );
        graphViewer.setLayoutAlgorithm( layoutAlgorithm, false );
        graphViewer.setFilters( new ViewerFilter[] { new NodeFilter( this ) } );
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
        // do nothing
    }

    public int getFilterLimit() {
        return filterLimit;
    }

    private void setFilterLimit( int filterLimit ) {
        this.filterLimit = filterLimit;
    }

    @Override
    public void dispose() {
        getUsusModel().removeUsusModelListener( listener );
        super.dispose();
    }

    private void initUsusModelListener() {
        listener = new IUsusModelListener() {
            public void ususModelChanged( ) {
                Display.getDefault().asyncExec( new Runnable() {
                    public void run() {
                        model.invalidate();
                        drawGraphConditionally();
                    }
                } );
            }
        };
        getUsusModel().addUsusModelListener( listener );
    }

    private void updateSpinnerAndFilter() {
        int maxFilterValue = model.getMaxFilterValue();
        initFilterLimit( maxFilterValue );
        scale.setMaximum( calcMaxFilterValue( maxFilterValue ) );
    }

    protected int calcMaxFilterValue( int maxFilterValue ) {
        return maxFilterValue;
    }

}
