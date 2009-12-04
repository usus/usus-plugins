// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.ViewPart;
import org.projectusus.ui.internal.viewer.IColumnDesc;

public abstract class TableViewPart<T> extends ViewPart {

    protected TableViewer viewer;

    protected abstract IColumnDesc<T>[] getColumns();

    protected abstract IStructuredContentProvider getContentProvider();

    protected abstract ColumnDescLabelProvider<T> getLabelProvider();

    protected void createViewer( Composite parent ) {
        Table table = createTable( parent );
        viewer = new TableViewer( table );
        viewer.setContentProvider( getContentProvider() );
        viewer.setLabelProvider( getLabelProvider() );
    }

    protected Table createTable( Composite parent ) {
        Composite comp = new Composite( parent, SWT.NONE );

        int style = SWT.SINGLE | SWT.FULL_SELECTION | SWT.V_SCROLL;
        Table result = new Table( comp, style );
        TableColumnLayout layout = new TableColumnLayout();
        comp.setLayout( layout );

        result.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
        result.setLinesVisible( true );
        result.setHeaderVisible( true );

        createColumns( result, layout );
        return result;
    }

    protected void createColumns( Table table, TableColumnLayout layout ) {
        for( IColumnDesc<T> columnDesc : getColumns() ) {
            TableColumn column = new TableColumn( table, SWT.NONE );
            ColumnWeightData data = new ColumnWeightData( columnDesc.getWeight() );
            layout.setColumnData( column, data );
            column.setText( columnDesc.getHeadLabel() );
            column.pack();
        }
    }

    @Override
    public void setFocus() {
        Control control = viewer.getControl();
        if( control != null && !control.isDisposed() ) {
            control.setFocus();
        }
    }

}
