// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.coveredprojects;

import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.part.ViewPart;
import org.projectusus.core.internal.project.FindUsusProjects;
import org.projectusus.core.internal.project.IUSUSProject;


public class CoveredProjectsView extends ViewPart {

    private CheckboxTableViewer viewer;

    @Override
    public void createPartControl( Composite parent ) {
        createViewer( parent );
        configureActionBars();
        refresh();
    }

    @Override
    public void setFocus() {
        Control control = viewer.getControl();
        if( control != null && !control.isDisposed() ) {
            control.setFocus();
        }
    }

    // internal methods
    // /////////////////

    private void createViewer( Composite parent ) {
        Table table = createTable( parent );
        viewer = new CheckboxTableViewer( table );
        viewer.setContentProvider( new CoveredProjectsCP() );
        viewer.setLabelProvider( new CoveredProjectsLP() );
        viewer.addCheckStateListener( new ICheckStateListener() {
            public void checkStateChanged( CheckStateChangedEvent event ) {
                updateUsusProject( event.getElement(), event.getChecked() );
                refresh();
            }
        } );
    }

    private void updateUsusProject( Object element, boolean checked ) {
        if( element instanceof IProject ) {
            IProject project = (IProject)element;
            if( project.isAccessible() ) {
                Object adapter = project.getAdapter( IUSUSProject.class );
                if( adapter instanceof IUSUSProject ) {
                    IUSUSProject ususProject = (IUSUSProject)adapter;
                    ususProject.setUsusProject( checked );
                }
            }
        }
    }

    private Table createTable( Composite parent ) {
        Composite comp = new Composite( parent, SWT.NONE );

        int style = SWT.CHECK | SWT.SINGLE | SWT.FULL_SELECTION | SWT.V_SCROLL;
        Table result = new Table( comp, style );
        TableColumnLayout layout = new TableColumnLayout();
        comp.setLayout( layout );

        result.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
        result.setLinesVisible( true );
        result.setHeaderVisible( true );

        createColumns( result, layout );
        return result;
    }

    private void createColumns( Table table, TableColumnLayout layout ) {
        for( ColumnDesc columnDesc : ColumnDesc.values() ) {
            TableColumn column = new TableColumn( table, SWT.NONE );
            ColumnWeightData data = new ColumnWeightData( columnDesc.getWeight() );
            layout.setColumnData( column, data );
            column.setText( columnDesc.getHeadLabel() );
            column.pack();
        }
    }

    private void configureActionBars() {
        IActionBars bars = getViewSite().getActionBars();
        enableContributions( bars.getToolBarManager() );
        enableContributions( bars.getMenuManager() );
    }

    private void enableContributions( IContributionManager manager ) {
        manager.add( new Separator( IWorkbenchActionConstants.MB_ADDITIONS ) );
    }

    private void refresh() {
        IWorkspaceRoot input = getWorkspace().getRoot();
        viewer.setInput( input );
        applyCheckedState( input );
    }

    private void applyCheckedState( IWorkspaceRoot input ) {
        IContentProvider cp = viewer.getContentProvider();
        Object[] elements = ((IStructuredContentProvider)cp).getElements( input );
        List<IProject> ususProjects = new FindUsusProjects( elements ).compute();
        viewer.setCheckedElements( ususProjects.toArray() );
    }
}
