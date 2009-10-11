// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions;

import java.util.List;

import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.part.ViewPart;
import org.projectusus.core.internal.proportions.CodeProportion;
import org.projectusus.core.internal.proportions.IUsusModelListener;
import org.projectusus.core.internal.proportions.IUsusModelStatus;
import org.projectusus.core.internal.proportions.UsusModel;

public class CockpitView extends ViewPart {

    private TreeViewer treeViewer;
    private IUsusModelListener listener;

    @Override
    public void createPartControl( Composite parent ) {
        createViewer( parent );
        initListener();
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
        UsusModel.getInstance().removeUsusModelListener( listener );
        super.dispose();
    }

    private void initListener() {
        listener = new IUsusModelListener() {
            public void ususModelChanged( IUsusModelStatus lastStatus, List<CodeProportion> entries ) {
                Display.getDefault().asyncExec( new Runnable() {
                    public void run() {
                        refresh();
                    }
                } );
            }
        };
        UsusModel.getInstance().addUsusModelListener( listener );
    }

    private void refresh() {
        if( treeViewer != null && !treeViewer.getControl().isDisposed() ) {
            treeViewer.refresh();
            treeViewer.expandAll();
        }
    }

    private void createViewer( Composite parent ) {
        parent.setLayout( new FillLayout() );
        treeViewer = new TreeViewer( createTree( parent ) );
        treeViewer.setUseHashlookup( true );

        createColumns( new TreeColumn[0] );

        treeViewer.setLabelProvider( new CockpitLP() );
        treeViewer.setContentProvider( new CockpitCP() );
        treeViewer.setInput( UsusModel.getInstance() );
    }

    private void createColumns( TreeColumn[] currentColumns ) {
        Tree tree = treeViewer.getTree();
        TableLayout layout = new TableLayout();
        treeViewer.getTree().setLayout( layout );

        TreeViewerColumn column = new TreeViewerColumn( treeViewer, SWT.NONE );
        column.getColumn().setResizable( true );
        column.getColumn().setMoveable( true );
        column.getColumn().setText( "Latest analysis results" );
        layout.addColumnData( new ColumnWeightData( 50 ) );

        tree.setLinesVisible( true );
        tree.setHeaderVisible( true );
        tree.layout( true );
    }

    private Tree createTree( Composite parent ) {
        int style = SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI | SWT.FULL_SELECTION;
        Tree tree = new Tree( parent, style );
        tree.setLinesVisible( true );
        return tree;
    }
}
