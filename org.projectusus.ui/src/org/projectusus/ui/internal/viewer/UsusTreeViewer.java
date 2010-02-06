// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.viewer;

import static org.eclipse.swt.layout.GridData.FILL_BOTH;

import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;

public class UsusTreeViewer<T> extends TreeViewer {

    private final static int STYLE = SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI | SWT.FULL_SELECTION;

    public UsusTreeViewer( Composite parent, IColumnDesc<T>[] columns ) {
        super( new Tree( parent, STYLE ) );
        TableLayout layout = createTree();
        createColumns( layout, columns );
    }

    private TableLayout createTree() {
        TableLayout layout = new TableLayout();

        Tree tree = getTree();
        tree.setLayout( layout );
        tree.setLayoutData( new GridData( FILL_BOTH ) );
        tree.setLinesVisible( true );
        tree.setHeaderVisible( true );
        tree.layout( true );

        return layout;
    }

    private void createColumns( TableLayout layout, IColumnDesc<T>[] columns ) {
        for( IColumnDesc<T> desc : columns ) {
            int style = desc.getColumnAlignment().toSwtStyle();
            TreeViewerColumn column = new TreeViewerColumn( this, style );
            column.getColumn().setResizable( true );
            column.getColumn().setMoveable( true );
            column.getColumn().setText( desc.getHeadLabel() );
            layout.addColumnData( new ColumnWeightData( desc.getWeight() ) );
        }
    }
}
