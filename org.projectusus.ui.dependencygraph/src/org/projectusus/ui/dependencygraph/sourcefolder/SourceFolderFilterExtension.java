package org.projectusus.ui.dependencygraph.sourcefolder;

import static java.util.Collections.emptySet;
import static org.eclipse.jface.action.ActionContributionItem.MODE_FORCE_TEXT;
import static org.projectusus.core.statistics.UsusModelProvider.ususModel;
import static org.projectusus.ui.dependencygraph.sourcefolder.SourceFolderChangeDetector.detectSourceFolderChange;

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
import org.eclipse.jface.viewers.ViewerFilter;
import org.projectusus.core.IUsusModelListener;
import org.projectusus.core.project.FindUsusProjects;
import org.projectusus.ui.dependencygraph.common.IRefreshable;
import org.projectusus.ui.dependencygraph.filters.SourceFolderFilter;
import org.projectusus.ui.dependencygraph.handlers.SourceFolderFilterAction;
import org.projectusus.ui.internal.UsusUIPlugin;

public class SourceFolderFilterExtension {

    private final IRefreshable refreshable;
    private final SourceFolderFilter filter = new SourceFolderFilter();
    private final SourceFolderFilterAction action;

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

    public SourceFolderFilterExtension( IRefreshable refreshable ) {
        this.refreshable = refreshable;
        action = new SourceFolderFilterAction( filter, refreshable );
    }

    public void initialize() {
        ususModel().addUsusModelListener( ususModelListener );
        JavaCore.addElementChangedListener( elementChangedListener );
        recomputeSourceFolders();
    }

    public ViewerFilter getFilter() {
        return filter;
    }

    public void contributeTo( ToolBarManager toolBarManager ) {
        ActionContributionItem actionContribution = new ActionContributionItem( action );
        actionContribution.setMode( MODE_FORCE_TEXT );
        toolBarManager.add( actionContribution );
    }

    public void dispose() {
        JavaCore.removeElementChangedListener( elementChangedListener );
        ususModel().removeUsusModelListener( ususModelListener );
    }

    private void recomputeSourceFolders() {
        IProject[] allProjects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
        List<IProject> ususProjects = new FindUsusProjects( allProjects ).compute();
        IJavaProject[] ususJavaProjects = convertToJavaProjects( ususProjects );
        Set<IPath> allSourceFolders = scanForSourceFolders( ususJavaProjects );

        filter.updateSourceFolders( new LinkedList<IPath>( allSourceFolders ) );
        action.updateState();
        refreshable.refresh();
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
