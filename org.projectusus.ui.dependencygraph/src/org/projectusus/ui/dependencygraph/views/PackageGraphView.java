package org.projectusus.ui.dependencygraph.views;

import java.util.Set;

import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.projectusus.core.IUsusModelListener;
import org.projectusus.core.statistics.UsusModelProvider;
import org.projectusus.ui.dependencygraph.colorProvider.PackageEdgeColorProvider;
import org.projectusus.ui.dependencygraph.common.DependencyGraphModel;
import org.projectusus.ui.dependencygraph.common.DependencyGraphView;
import org.projectusus.ui.dependencygraph.common.DependencyGraphViewer;
import org.projectusus.ui.dependencygraph.nodes.GraphNode;
import org.projectusus.ui.dependencygraph.nodes.PackageRepresenter;

public class PackageGraphView extends DependencyGraphView {

    public static final String VIEW_ID = "org.projectusus.ui.dependencygraph.PackageGraphView";

    private static final String ONLY_IN_CYCLES = "Only cyclic dependencies";

    private static final DependencyGraphModel packageGraphModel = new DependencyGraphModel() {

        @Override
        protected Set<? extends GraphNode> getRefreshedNodes() {
            return PackageRepresenter.getAllPackages();
        }
    };

    private static final PackageEdgeColorProvider packageEdgeColorProvider = new PackageEdgeColorProvider();

    private boolean highlightStrongConnections = false;

    private final IUsusModelListener listener;

    public PackageGraphView() {
        super( VIEW_ID, packageGraphModel, packageEdgeColorProvider );
        listener = new IUsusModelListener() {
            public void ususModelChanged() {
                packageEdgeColorProvider.calculatePackageRelations();
            }
        };
        UsusModelProvider.ususModel().addUsusModelListener( listener );
    }

    @Override
    public void dispose() {
        UsusModelProvider.ususModel().removeUsusModelListener( listener );
        super.dispose();
    }

    @Override
    public String getFilenameForScreenshot() {
        return "usus-package-graph";
    }

    @Override
    protected String getRestrictingCheckboxLabelName() {
        return ONLY_IN_CYCLES;
    }

    @Override
    protected Control createAdditionalWidgets( Composite filterArea ) {
        Composite composite = new Composite( filterArea, SWT.NONE );
        composite.setLayout( GridLayoutFactory.fillDefaults().numColumns( 4 ).spacing( 10, 0 ).create() );

        createRestrictingCheckBox( composite );
        createHighlightCheckbox( composite );

        return composite;

    }

    private void createHighlightCheckbox( Composite composite ) {
        final Button checkbox = new Button( composite, SWT.CHECK );
        checkbox.setText( "Highlight strong connections" );
        checkbox.setToolTipText( "Saturates edges based on the number of relations among packages" );
        checkbox.addSelectionListener( highlightCheckboxSelectionAdapter() );
        checkbox.setSelection( highlightStrongConnections );
    }

    private SelectionAdapter highlightCheckboxSelectionAdapter() {
        return new SelectionAdapter() {
            @Override
            public void widgetSelected( final SelectionEvent e ) {
                Display.getDefault().asyncExec( new Runnable() {
                    public void run() {
                        Button source = (Button)e.getSource();
                        highlightStrongConnections = source.getSelection();
                        DependencyGraphViewer graphViewer = (DependencyGraphViewer)getZoomableViewer();
                        graphViewer.setHighlightStrongConnections( highlightStrongConnections );
                        graphViewer.refresh();
                    }
                } );
            }
        };
    }
}
