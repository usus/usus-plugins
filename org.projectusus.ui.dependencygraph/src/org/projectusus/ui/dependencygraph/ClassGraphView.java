package org.projectusus.ui.dependencygraph;

import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;
import static org.eclipse.jface.action.ActionContributionItem.MODE_FORCE_TEXT;
import static org.projectusus.core.statistics.UsusModelProvider.ususModel;
import static org.projectusus.ui.dependencygraph.sourcefolder.SourceFolderChangeDetector.detectSourceFolderChange;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IElementChangedListener;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.projectusus.core.IUsusModelListener;
import org.projectusus.core.project.FindUsusProjects;
import org.projectusus.ui.dependencygraph.common.DependencyGraphModel;
import org.projectusus.ui.dependencygraph.common.DependencyGraphView;
import org.projectusus.ui.dependencygraph.filters.SourceFolderFilter;
import org.projectusus.ui.dependencygraph.handlers.SourceFolderFilterAction;
import org.projectusus.ui.dependencygraph.nodes.ClassRepresenter;
import org.projectusus.ui.dependencygraph.nodes.GraphNode;
import org.projectusus.ui.dependencygraph.sourcefolder.SourceFolderScanner;
import org.projectusus.ui.internal.UsusUIPlugin;

public class ClassGraphView extends DependencyGraphView {

    public static final String VIEW_ID = ClassGraphView.class.getName();

    private static final String ONLY_CROSS_PACKAGE = "Only cross-package relations";

    private static final DependencyGraphModel classGraphModel = new DependencyGraphModel() {

        @Override
        protected Set<? extends GraphNode> getRefreshedNodes() {
            return ClassRepresenter.getAllClassRepresenters();
        }
    };

    private final ToolBarManager toolBarManager = new ToolBarManager( SWT.HORIZONTAL | SWT.RIGHT );

    private final SourceFolderFilter sourceFolderFilter = new SourceFolderFilter();
    private final SourceFolderFilterAction sourceFolderFilterAction = new SourceFolderFilterAction( sourceFolderFilter, new Runnable() {
        public void run() {
            refresh();
        }
    } );

    private final IUsusModelListener ususModelListener = new IUsusModelListener() {
        public void ususModelChanged() {
            recomputeSourceFolders();
        }
    };

    private final IElementChangedListener elementChangedListener = new IElementChangedListener() {
        public void elementChanged( ElementChangedEvent event ) {
            if( detectSourceFolderChange( event.getDelta() ) ) {
                recomputeSourceFolders();
            }
        }
    };

    private Composite additionalWidgetsComposite;

    public ClassGraphView() {
        super( classGraphModel );
    }

    @Override
    public void createPartControl( Composite parent ) {
        super.createPartControl( parent );
        ususModel().addUsusModelListener( ususModelListener );
        JavaCore.addElementChangedListener( elementChangedListener );

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
        JavaCore.removeElementChangedListener( elementChangedListener );
        ususModel().removeUsusModelListener( ususModelListener );
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
        ActionContributionItem actionContribution = new ActionContributionItem( sourceFolderFilterAction );
        actionContribution.setMode( MODE_FORCE_TEXT );
        toolBarManager.add( actionContribution );
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

    private void recomputeSourceFolders() {
        IProject[] allProjects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
        List<IProject> ususProjects = new FindUsusProjects( allProjects ).compute();
        IJavaProject[] ususJavaProjects = convertToJavaProjects( ususProjects );
        Set<IPath> allSourceFolders = scanForSourceFolders( ususJavaProjects );

        sourceFolderFilter.updateSourceFolders( new LinkedList<IPath>( allSourceFolders ) );
        sourceFolderFilterAction.updateState();
        refresh();
    }

    private Set<IPath> scanForSourceFolders( IJavaProject[] ususJavaProjects ) {
        try {
            return new SourceFolderScanner().scan( ususJavaProjects );
        } catch( JavaModelException e ) {
            UsusUIPlugin.getDefault().log( e );
            return emptySet();
        }
    }

    private IJavaProject[] convertToJavaProjects( List<IProject> ususProjects ) {
        List<IJavaProject> result = new LinkedList<IJavaProject>();
        for( IProject project : ususProjects ) {
            result.add( JavaCore.create( project ) );
        }
        return result.toArray( new IJavaProject[result.size()] );
    }

}
