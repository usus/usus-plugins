// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions;

import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;

class CockpitTreeViewer extends TreeViewer {

    private final static int STYLE = SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI | SWT.FULL_SELECTION;

    CockpitTreeViewer( Composite parent ) {
        super( new Tree( parent, STYLE ) );
        setUseHashlookup( true );

        createTreeTable();

        setLabelProvider( new CockpitLP() );
        setContentProvider( new CockpitCP() );
    }

    private void createTreeTable() {
        Tree tree = getTree();
        TableLayout layout = new TableLayout();
        getTree().setLayout( layout );
        createColumns( layout );
        tree.setLinesVisible( true );
        tree.setHeaderVisible( true );
        tree.layout( true );
    }

    private void createColumns( TableLayout layout ) {
        for( CockpitColumnDesc desc : CockpitColumnDesc.values() ) {
            TreeViewerColumn column = new TreeViewerColumn( this, SWT.NONE );
            column.getColumn().setResizable( true );
            column.getColumn().setMoveable( true );
            column.getColumn().setText( desc.getHeadLabel() );
            layout.addColumnData( new ColumnWeightData( desc.getWeight() ) );
        }
    }
}
