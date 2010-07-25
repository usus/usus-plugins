// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.cockpit;

import static org.projectusus.ui.internal.AnalysisDisplayModel.displayModel;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.part.ViewPart;
import org.projectusus.ui.internal.AnalysisDisplayModel;
import org.projectusus.ui.internal.IDisplayModelListener;
import org.projectusus.ui.internal.proportions.actions.OpenHotspots;
import org.projectusus.ui.internal.proportions.actions.RefreshHotspots;
import org.projectusus.ui.internal.proportions.actions.ToggleAutoCompute;
import org.projectusus.ui.internal.selection.ExtractCodeProportion;

public class CockpitView extends ViewPart {

    private CockpitTreeViewer treeViewer;
    private IDisplayModelListener listener;

    @Override
    public void createPartControl( Composite parent ) {
        createViewer( parent );
        initOpenBehavior();
        initActionBars();
        initContextMenuBehavior();
        initModelListener();
        getViewSite().setSelectionProvider( treeViewer );
    }

    @Override
    public void setFocus() {
        if( treeViewer != null && !treeViewer.getControl().isDisposed() ) {
            treeViewer.getControl().setFocus();
        }
    }

    @Override
    public void dispose() {
        displayModel().removeModelListener( listener );
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
        MenuManager menuManager = new MenuManager( "#PopupMenu" );
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
        new ExtractCodeProportion( selection ).compute();
    }

    private void initActionBars() {
        IMenuManager menuManager = getViewSite().getActionBars().getMenuManager();
        menuManager.add( new ToggleAutoCompute() );
        menuManager.add( new Separator( IWorkbenchActionConstants.MB_ADDITIONS ) );
    }

    private void initModelListener() {
        listener = new IDisplayModelListener() {
            public void updateCategories( final AnalysisDisplayModel model ) {
                Display.getDefault().asyncExec( new Runnable() {
                    public void run() {
                        handleDisplayModelChanged( model );
                    }
                } );
            }
        };
        displayModel().addModelListener( listener );
    }

    private void handleDisplayModelChanged( AnalysisDisplayModel model ) {
        refresh( model );
    }

    private void refresh( AnalysisDisplayModel model ) {
        if( treeViewer != null && !treeViewer.getControl().isDisposed() ) {
            ISelection selection = treeViewer.getSelection();
            treeViewer.setInput( model );
            treeViewer.expandAll();
            if( !selection.isEmpty() ) {
                treeViewer.selectInTree( ((TreeSelection)selection).getFirstElement() );
                new RefreshHotspots( model ).run();
            }
        }
    }

    private void createViewer( Composite parent ) {
        parent.setLayout( new FillLayout() );
        treeViewer = new CockpitTreeViewer( parent );
    }

}
