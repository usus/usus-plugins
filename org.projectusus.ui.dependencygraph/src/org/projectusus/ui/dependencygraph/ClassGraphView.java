package org.projectusus.ui.dependencygraph;

import static java.util.Collections.singleton;
import static org.projectusus.core.statistics.UsusModelProvider.ususModel;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;
import org.projectusus.core.IUsusModelListener;
import org.projectusus.ui.dependencygraph.common.DependencyGraphModel;
import org.projectusus.ui.dependencygraph.common.DependencyGraphView;
import org.projectusus.ui.dependencygraph.filters.SourceFolderFilter;
import org.projectusus.ui.dependencygraph.handlers.SourceFolderFilterAction;
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

    private final SourceFolderFilter sourceFolderFilter = new SourceFolderFilter();

    private final IUsusModelListener ususModelListener = new IUsusModelListener() {
        public void ususModelChanged() {
            recomputeSourceFolders();
        }
    };

    public ClassGraphView() {
        super( classGraphModel );
    }

    @Override
    public void createPartControl( Composite parent ) {
        super.createPartControl( parent );
        ususModel().addUsusModelListener( ususModelListener );
        recomputeSourceFolders();
    }

    @Override
    protected Collection<? extends ViewerFilter> createAdditionalFilters() {
        return singleton( sourceFolderFilter );
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
        ususModel().removeUsusModelListener( ususModelListener );
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
        toolBarManager.add( new SourceFolderFilterAction( sourceFolderFilter, this ) );
        toolBarManager.update( true );
    }

    private void recomputeSourceFolders() {
        // TODO aOSD Get all projects from ResourcePlugin
        // TODO aOSD Filter for Usus projects using FindUsusProjects
        // TODO aOSD Scan for source folders
        List<IPath> allSourceFolders = new LinkedList<IPath>();
        allSourceFolders.add( Path.fromPortableString( "src" ) );
        allSourceFolders.add( Path.fromPortableString( "test" ) );

        sourceFolderFilter.updateSourceFolders( allSourceFolders );
    }

}
