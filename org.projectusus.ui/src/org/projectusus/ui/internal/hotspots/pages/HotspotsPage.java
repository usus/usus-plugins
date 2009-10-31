// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.hotspots.pages;

import static java.util.Arrays.asList;
import static org.eclipse.swt.layout.GridData.FILL_BOTH;

import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.part.Page;
import org.projectusus.core.internal.proportions.model.CodeProportion;
import org.projectusus.core.internal.proportions.model.IHotspot;
import org.projectusus.ui.internal.hotspots.actions.OpenHotspotInEditor;

public class HotspotsPage extends Page implements IHotspotsPage {

    protected TreeViewer viewer;
    private final IHotspotsPageColumnDesc[] columnDescs;

    public HotspotsPage( IHotspotsPageColumnDesc[] columnDescs ) {
        this.columnDescs = columnDescs;
    }

    public boolean isInitialized() {
        return viewer != null;
    }

    protected void createViewer( Composite parent ) {
        viewer = new TreeViewer( createTree( parent ) );
        createTableLayout();
        viewer.setLabelProvider( new HotspotsLP( asList( columnDescs ) ) );
        viewer.setContentProvider( new HotspotsCP() );
        viewer.getControl().setLayoutData( new GridData( FILL_BOTH ) );
    }

    protected void initOpenListener() {
        viewer.addOpenListener( new IOpenListener() {
            public void open( OpenEvent event ) {
                IHotspot hotspot = extractHotspot( event.getSelection() );
                if( hotspot != null ) {
                    new OpenHotspotInEditor( hotspot ).run();
                }
            }
        } );
    }

    public void setInput( CodeProportion codeProportion ) {
        viewer.setInput( codeProportion );
    }

    @Override
    public void createControl( Composite parent ) {
        createViewer( parent );
        initOpenListener();
    }

    @Override
    public Control getControl() {
        return viewer.getControl();
    }

    @Override
    public void setFocus() {
        if( viewer != null && !viewer.getControl().isDisposed() ) {
            viewer.getControl().setFocus();
        }
    }

    private IHotspot extractHotspot( ISelection selection ) {
        IHotspot result = null;
        if( !selection.isEmpty() && selection instanceof IStructuredSelection ) {
            Object element = ((IStructuredSelection)selection).getFirstElement();
            if( element instanceof IHotspot ) {
                result = (IHotspot)element;
            }
        }
        return result;
    }

    private Tree createTree( Composite parent ) {
        int style = SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI | SWT.FULL_SELECTION;
        Tree result = new Tree( parent, style );
        result.setLinesVisible( true );
        result.setHeaderVisible( true );
        return result;
    }

    private void createTableLayout() {
        Tree tree = viewer.getTree();
        TableLayout layout = new TableLayout();
        tree.setLayout( layout );
        createColumns( layout );
    }

    private void createColumns( TableLayout layout ) {
        for( IHotspotsPageColumnDesc columnDesc : asList( columnDescs ) ) {
            TreeViewerColumn column = new TreeViewerColumn( viewer, SWT.NONE );
            column.getColumn().setResizable( true );
            column.getColumn().setMoveable( true );
            column.getColumn().setText( columnDesc.getHeadLabel() );
            layout.addColumnData( new ColumnWeightData( columnDesc.getWeight() ) );
        }
        viewer.getTree().layout();
    }
}
