package org.projectusus.ui.dependencygraph;

import static java.util.Collections.singleton;
import static org.eclipse.jface.action.IAction.AS_CHECK_BOX;

import java.util.Collection;
import java.util.Set;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.projectusus.ui.dependencygraph.common.DependencyGraphModel;
import org.projectusus.ui.dependencygraph.common.DependencyGraphView;
import org.projectusus.ui.dependencygraph.filters.SourceFolderFilter;
import org.projectusus.ui.dependencygraph.nodes.ClassRepresenter;
import org.projectusus.ui.dependencygraph.nodes.GraphNode;

public class ClassGraphView extends DependencyGraphView {

    public static final String VIEW_ID = ClassGraphView.class.getName();

    private static final String ONLY_CROSS_PACKAGE = "Only cross-package relations";

    private static final DependencyGraphModel classGraphModel = new DependencyGraphModel() {

        @Override
        protected Set<? extends GraphNode> getRefreshedNodes() {
            return ClassRepresenter.getAllClassRepresenters();
        }
    };

    private final ToolBarManager toolBarManager = new ToolBarManager( SWT.HORIZONTAL | SWT.FLAT );

    public ClassGraphView() {
        super( classGraphModel );
    }

    @Override
    protected Collection<? extends ViewerFilter> createAdditionalFilters() {
        return singleton( new SourceFolderFilter( "src/main/java" ) );
    }

    @Override
    public String getFilenameForScreenshot() {
        return "usus-class-graph";
    }

    @Override
    protected String getCheckboxLabelName() {
        return ONLY_CROSS_PACKAGE;
    }

    @Override
    public void dispose() {
        toolBarManager.dispose();
        super.dispose();
    }

    @Override
    protected Control createAdditionalWidgets( Composite filterArea ) {
        Composite composite = new Composite( filterArea, SWT.NONE );
        composite.setLayout( GridLayoutFactory.fillDefaults().numColumns( 2 ).spacing( 10, 0 ).create() );

        toolBarManager.createControl( composite );
        createRestrictingCheckBox( composite );

        return composite;
    }

    @Override
    protected void contributeTo( IActionBars actionBars ) {
        super.contributeTo( actionBars );
        toolBarManager.add( new Action( "All source folders", AS_CHECK_BOX ) {
            @Override
            public void runWithEvent( Event event ) {
                Shell shell = event.display.getActiveShell();
                MessageDialog.openInformation( shell, "Choose source folders", "TODO" );
            }
        } );
        toolBarManager.update( true );
    }

}
