// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;
import org.projectusus.core.internal.proportions.CodeProportion;

public class HotSpotsView extends ViewPart {

    private TableViewer tableViewer;

    @Override
    public void createPartControl( Composite parent ) {
        createTableViewer( parent );
        initSelectionListener();
    }

    @Override
    public void setFocus() {
        if( tableViewer != null && !tableViewer.getControl().isDisposed() ) {
            tableViewer.getControl().setFocus();
        }
    }

    private void createTableViewer( Composite parent ) {
        tableViewer = new TableViewer( parent );
        tableViewer.setLabelProvider( new LabelProvider() );
        tableViewer.setContentProvider( new CodeProportionsCockpitCP() );
    }

    private void initSelectionListener() {
        ISelectionService service = getViewSite().getWorkbenchWindow().getSelectionService();
        service.addSelectionListener( new ISelectionListener() {
            public void selectionChanged( IWorkbenchPart part, ISelection selection ) {
                if( part instanceof CodeProportionsCockpit && selection instanceof IStructuredSelection ) {
                    IStructuredSelection ssel = (IStructuredSelection)selection;
                    Object element = ssel.getFirstElement();
                    if( element instanceof CodeProportion ) {
                        CodeProportion codeProportion = (CodeProportion)element;
                        tableViewer.setInput( codeProportion.getHotspots() );
                    }
                }
            }
        } );
    }
}
