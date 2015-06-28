package org.projectusus.ui.dependencygraph.views;

import static java.util.Collections.singleton;

import java.util.Collection;
import java.util.Set;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.projectusus.ui.dependencygraph.colorProvider.ClassEdgeColorProvider;
import org.projectusus.ui.dependencygraph.common.DependencyGraphModel;
import org.projectusus.ui.dependencygraph.common.DependencyGraphView;
import org.projectusus.ui.dependencygraph.nodes.ClassRepresenter;
import org.projectusus.ui.dependencygraph.nodes.GraphNode;
import org.projectusus.ui.dependencygraph.sourcefolder.SourceFolderFilterExtension;

public class ClassGraphView extends DependencyGraphView {

    public static final String VIEW_ID = "org.projectusus.ui.dependencygraph.ClassGraphView";

    private static final String ONLY_CROSS_PACKAGE = "Only cross-package relations";

    private static final DependencyGraphModel classGraphModel = new DependencyGraphModel() {

        @Override
        protected Set<? extends GraphNode> getRefreshedNodes() {
            return ClassRepresenter.getAllClassRepresenters();
        }
    };
    private final static ClassEdgeColorProvider classEdgeColorProvider = new ClassEdgeColorProvider();

    private final ToolBarManager toolBarManager = new ToolBarManager( SWT.HORIZONTAL | SWT.RIGHT );

    private final SourceFolderFilterExtension sourceFolderFilterExtension;

    private Composite additionalWidgetsComposite;

    public ClassGraphView() {
        super( VIEW_ID, classGraphModel, classEdgeColorProvider );
        sourceFolderFilterExtension = new SourceFolderFilterExtension( this );
    }

    @Override
    public void createPartControl( Composite parent ) {
        super.createPartControl( parent );
        sourceFolderFilterExtension.initialize();
    }

    @Override
    protected Collection<? extends ViewerFilter> createAdditionalFilters() {
        return singleton( sourceFolderFilterExtension.getFilter() );
    }

    @Override
    public String getFilenameForScreenshot() {
        return "usus-class-graph";
    }

    @Override
    protected String getRestrictingCheckboxLabelName() {
        return ONLY_CROSS_PACKAGE;
    }

    @Override
    public void dispose() {
        sourceFolderFilterExtension.dispose();
        toolBarManager.dispose();
        super.dispose();
    }

    @Override
    protected Control createAdditionalWidgets( Composite filterArea ) {
        additionalWidgetsComposite = new Composite( filterArea, SWT.NONE );
        additionalWidgetsComposite.setLayout( GridLayoutFactory.fillDefaults().numColumns( 2 ).spacing( 10, 0 ).create() );

        createAdditionalToolbar( additionalWidgetsComposite );
        createRestrictingCheckBox( additionalWidgetsComposite );

        return additionalWidgetsComposite;
    }

    private void createAdditionalToolbar( Composite parent ) {
        sourceFolderFilterExtension.contributeTo( toolBarManager );
        toolBarManager.createControl( parent );
    }

    @Override
    public void refresh() {
        Display.getDefault().asyncExec( new Runnable() {
            public void run() {
                additionalWidgetsComposite.layout();
                ClassGraphView.super.refresh();
            }
        } );
    }

}
