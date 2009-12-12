// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.cockpit;

import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.projectusus.core.internal.UsusCorePlugin.getUsusModel;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.CW;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.TA;
import static org.projectusus.ui.internal.util.UITexts.cockpitView_noProjectsSelected;
import static org.projectusus.ui.internal.util.UsusUIImages.getSharedImages;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.part.ViewPart;
import org.projectusus.core.internal.project.FindUsusProjects;
import org.projectusus.core.internal.proportions.IUsusModelListener;
import org.projectusus.core.internal.proportions.model.CodeProportion;
import org.projectusus.core.internal.proportions.model.IUsusElement;
import org.projectusus.core.internal.proportions.modelupdate.IUsusModelHistory;
import org.projectusus.core.internal.proportions.modelupdate.IUsusModelStatus;
import org.projectusus.ui.internal.proportions.actions.OpenHotspots;
import org.projectusus.ui.internal.proportions.actions.ShowCoverageView;
import org.projectusus.ui.internal.proportions.actions.ShowProblemsView;
import org.projectusus.ui.internal.proportions.actions.ToggleAutoCompute;
import org.projectusus.ui.internal.selection.ExtractCodeProportion;
import org.projectusus.ui.internal.util.ISharedUsusImages;

public class CockpitView extends ViewPart {

    private CockpitTreeViewer treeViewer;
    private IUsusModelListener listener;

    @Override
    public void createPartControl( Composite parent ) {
        createViewer( parent );
        initOpenBehavior();
        initActionBars();
        initContextMenuBehavior();
        initModelListener();
        getViewSite().setSelectionProvider( treeViewer );
        setMessage( getUsusModel().getHistory().getLastStatus() );
    }

    @Override
    public void setFocus() {
        if( treeViewer != null && !treeViewer.getControl().isDisposed() ) {
            treeViewer.getControl().setFocus();
        }
    }

    @Override
    public void dispose() {
        getUsusModel().removeUsusModelListener( listener );
        super.dispose();
    }

    private void initOpenBehavior() {
        treeViewer.addOpenListener( new IOpenListener() {
            public void open( OpenEvent event ) {
                new OpenHotspots( treeViewer.getSelection() ).run();
            }
        } );
    }

    private void initContextMenuBehavior() {
        MenuManager menuManager = new MenuManager( "#PopupMenu" ); //$NON-NLS-1$
        menuManager.addMenuListener( new IMenuListener() {
            public void menuAboutToShow( IMenuManager manager ) {
                manager.add( new OpenHotspots( treeViewer.getSelection() ) );
                manager.add( new Separator() );
                addContextActionsFor( manager, treeViewer.getSelection() );
            }
        } );
        menuManager.setRemoveAllWhenShown( true );
        Menu menu = menuManager.createContextMenu( treeViewer.getTree() );
        treeViewer.getTree().setMenu( menu );
        getSite().registerContextMenu( menuManager, treeViewer );
    }

    protected void addContextActionsFor( IMenuManager manager, ISelection selection ) {
        CodeProportion codeProportion = new ExtractCodeProportion( selection ).compute();
        if( codeProportion != null ) {
            if( codeProportion.getMetric() == TA ) {
                manager.add( new ShowCoverageView() );
            } else if( codeProportion.getMetric() == CW ) {
                manager.add( new ShowProblemsView() );
            }
        }
    }

    private void initActionBars() {
        IMenuManager menuManager = getViewSite().getActionBars().getMenuManager();
        menuManager.add( new ToggleAutoCompute() );
        menuManager.add( new Separator( IWorkbenchActionConstants.MB_ADDITIONS ) );
    }

    private void initModelListener() {
        listener = new IUsusModelListener() {
            public void ususModelChanged( final IUsusModelHistory history, List<IUsusElement> elements ) {
                Display.getDefault().asyncExec( new Runnable() {
                    public void run() {
                        handleUsusModelChanged( history );
                    }
                } );
            }
        };
        getUsusModel().addUsusModelListener( listener );
    }

    private void handleUsusModelChanged( final IUsusModelHistory history ) {
        if( hasUsusProjects() ) {
            enableViewer( true );
            refresh();
            setMessage( history.getLastStatus() );
        } else {
            enableViewer( false );
            getStatusLine().setMessage( getWarningImage(), cockpitView_noProjectsSelected );
        }
    }

    private boolean hasUsusProjects() {
        IProject[] wsProjects = getWorkspace().getRoot().getProjects();
        return new FindUsusProjects( wsProjects ).compute().size() > 0;
    }

    private void enableViewer( boolean enabled ) {
        if( treeViewer != null && !treeViewer.getControl().isDisposed() ) {
            treeViewer.getControl().setEnabled( enabled );
        }
    }

    private void setMessage( IUsusModelStatus status ) {
        Image image = status.isStale() ? getWarningImage() : null;
        getStatusLine().setMessage( image, status.toString() );
    }

    private Image getWarningImage() {
        return getSharedImages().getImage( ISharedUsusImages.OBJ_WARNINGS );
    }

    private IStatusLineManager getStatusLine() {
        return getViewSite().getActionBars().getStatusLineManager();
    }

    private void refresh() {
        if( treeViewer != null && !treeViewer.getControl().isDisposed() ) {
            treeViewer.refresh();
            treeViewer.expandAll();
        }
    }

    private void createViewer( Composite parent ) {
        parent.setLayout( new FillLayout() );
        treeViewer = new CockpitTreeViewer( parent );
        treeViewer.setInput( getUsusModel() );
    }
}
