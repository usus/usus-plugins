// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.hotspots;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.part.ViewPart;
import org.projectusus.core.internal.proportions.model.CodeProportion;
import org.projectusus.ui.internal.hotspots.pages.DefaultHotspotsPage;
import org.projectusus.ui.internal.hotspots.pages.IHotspotsPage;

public class HotSpotsView extends ViewPart {

    private PageBook book;
    private IHotspotsPage defaultPage;
    private IHotspotsPage activePage;

    @Override
    public void createPartControl( Composite parent ) {
        book = new PageBook( parent, SWT.NONE );

        defaultPage = new DefaultHotspotsPage();
        defaultPage.createControl( book );
        showPage( defaultPage );

        initSelectionListener();
    }

    @Override
    public void setFocus() {
        // first set focus on the page book, in case the page
        // doesn't properly handle setFocus
        if( book != null ) {
            book.setFocus();
        }
        // then set focus on the page, if any
        if( activePage != null ) {
            activePage.setFocus();
        }
    }

    @Override
    public void dispose() {
        activePage = null;
        if( defaultPage != null ) {
            defaultPage.dispose();
        }
        super.dispose();
    }

    // internal
    // ////////

    private void initSelectionListener() {
        getSelectionService().addSelectionListener( new ISelectionListener() {
            public void selectionChanged( IWorkbenchPart part, ISelection selection ) {
                CodeProportion codeProportion = extractCodeProportion( selection );
                if( codeProportion != null ) {
                    updatePage( selection, codeProportion );
                }
            }
        } );
    }

    private CodeProportion extractCodeProportion( ISelection selection ) {
        CodeProportion result = null;
        if( selection instanceof IStructuredSelection && !selection.isEmpty() ) {
            Object element = ((IStructuredSelection)selection).getFirstElement();
            if( element instanceof CodeProportion ) {
                result = (CodeProportion)element;
            }
        }
        return result;
    }

    private ISelectionService getSelectionService() {
        return getViewSite().getWorkbenchWindow().getSelectionService();
    }

    private void showPage( IHotspotsPage page ) {
        if( activePage == page ) {
            return;
        }
        activePage = page;
        Control pageControl = activePage.getControl();
        if( pageControl != null && !pageControl.isDisposed() ) {
            // Verify that the page control is not disposed
            // If we are closing, it may have already been disposed
            book.showPage( pageControl );
        }
    }

    private IHotspotsPage createPage( ISelection selection ) {
        IHotspotsPage page = new ExtractHotspotsPage( selection ).compute();
        if( page != null && !page.isInitialized() ) {
            page.createControl( book );
        }
        return page;
    }

    private void updatePage( ISelection selection, CodeProportion codeProportion ) {
        IHotspotsPage page = createPage( selection );
        if( page != null ) {
            page.setInput( codeProportion );
            showPage( page );
        } else {
            showPage( defaultPage );
        }
    }
}
