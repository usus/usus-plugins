package org.projectusus.ui.viewer;

import java.text.DecimalFormat;
import java.text.ParseException;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

class ColumnByLabelSorter extends ViewerComparator {
    private final static DecimalFormat formatter = new DecimalFormat( "#.##" ); //$NON-NLS-1$

    private final TreeColumn column;

    private final StructuredViewer viewer;

    private final int columnIndex;

    private boolean ascending = false;

    private final boolean numeric;

    public ColumnByLabelSorter( StructuredViewer viewer, TreeColumn column, int columnIndex, boolean numeric ) {
        this.column = column;
        this.viewer = viewer;
        this.columnIndex = columnIndex;
        this.numeric = numeric;
        this.column.addSelectionListener( new SelectionAdapter() {

            @Override
            public void widgetSelected( @SuppressWarnings( "unused" ) SelectionEvent e ) {
                sortAndActualizeSortOrder();
            }
        } );
    }

    void sortAndActualizeSortOrder() {
        if( viewer.getComparator() == this ) {
            ascending = !ascending;
        }
        Tree table = column.getParent();
        table.setSortColumn( column );
        table.setSortDirection( ascending ? SWT.UP : SWT.DOWN );
        if( viewer.getComparator() == this ) {
            viewer.refresh();
        }
        viewer.setComparator( this ); // does nothing when already this
    }

    @Override
    public int compare( @SuppressWarnings( "unused" ) Viewer viewr, Object o1, Object o2 ) {
        return (ascending ? 1 : -1) * doCompare( o1, o2 );
    }

    protected int doCompare( Object o1, Object o2 ) {
        ITableLabelProvider labelProvider = ((ITableLabelProvider)viewer.getLabelProvider());
        String text1 = labelProvider.getColumnText( o1, columnIndex );
        String text2 = labelProvider.getColumnText( o2, columnIndex );
        if( numeric ) {
            try {
                Number number1 = formatter.parse( text1 );
                Number number2 = formatter.parse( text2 );
                return (int)Math.signum( number1.doubleValue() - number2.doubleValue() );
            } catch( ParseException e ) {
                // OK
            }
        }
        return text1.compareTo( text2 );
    }
}
