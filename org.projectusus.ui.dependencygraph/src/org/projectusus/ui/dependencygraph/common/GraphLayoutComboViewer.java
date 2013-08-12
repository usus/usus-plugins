package org.projectusus.ui.dependencygraph.common;

import static java.util.Arrays.sort;

import java.util.Comparator;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class GraphLayoutComboViewer {

    private static final String LAYOUT_LABEL = "Layout:";

    public GraphLayoutComboViewer( Composite parent, ISelectionChangedListener listener ) {
        Composite composite = new Composite( parent, SWT.NONE );
        composite.setLayout( new FillLayout() );
        createLabel( parent, LAYOUT_LABEL );

        ComboViewer comboViewer = new ComboViewer( parent, SWT.READ_ONLY );
        comboViewer.setContentProvider( new ArrayContentProvider() );
        comboViewer.setLabelProvider( new LabelProvider() );
        comboViewer.setInput( allLayoutsSortedByTitle() );
        comboViewer.setSelection( new StructuredSelection( GraphLayout.getDefault() ) );
        comboViewer.addSelectionChangedListener( listener );
    }

    private GraphLayout[] allLayoutsSortedByTitle() {
        GraphLayout[] layouts = GraphLayout.values();
        sort( layouts, byTitle() );
        return layouts;
    }

    private Comparator<GraphLayout> byTitle() {
        return new Comparator<GraphLayout>() {
            public int compare( GraphLayout first, GraphLayout second ) {
                return first.title().compareTo( second.title() );
            }
        };
    }

    private Label createLabel( Composite parent, String labelText ) {
        Label label = new Label( parent, SWT.NONE );
        label.setText( labelText );
        return label;
    }

}
