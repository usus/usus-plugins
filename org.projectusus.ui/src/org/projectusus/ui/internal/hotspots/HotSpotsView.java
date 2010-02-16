// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.hotspots;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.part.ViewPart;
import org.projectusus.core.internal.proportions.model.CodeProportion;
import org.projectusus.ui.internal.hotspots.pages.DefaultHotspotsPage;
import org.projectusus.ui.internal.hotspots.pages.IHotspotsPage;

public class HotSpotsView extends ViewPart {

    private PageBook book;
    private IHotspotsPage defaultPage;
    private IHotspotsPage activePage;

    public void update( CodeProportion codeProportion ) {
        if( codeProportion != null ) {
            updatePage( codeProportion );
        }
    }

    @Override
    public void createPartControl( Composite parent ) {
        book = new PageBook( parent, SWT.NONE );

        defaultPage = new DefaultHotspotsPage();
        defaultPage.createControl( book );
        showPage( defaultPage );
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

    private void showPage( IHotspotsPage page ) {
        if( activePage != page ) {
            activePage = page;
            Control pageControl = activePage.getControl();
            if( pageControl != null && !pageControl.isDisposed() ) {
                // Verify that the page control is not disposed
                // If we are closing, it may have already been disposed
                book.showPage( pageControl );
            }
        }
    }

    private IHotspotsPage createPage( CodeProportion codeProportion ) {
        IHotspotsPage page = extractHotspotsPage( codeProportion );
        if( page != null && !page.isInitialized() ) {
            page.createControl( book );
        }
        return page;
    }

    private IHotspotsPage extractHotspotsPage( CodeProportion codeProportion ) {
        IHotspotsPage result = null;
        Object adapter = codeProportion.getAdapter( IHotspotsPage.class );
        if( adapter instanceof IHotspotsPage ) {
            result = (IHotspotsPage)adapter;
        }
        return result;
    }

    private void updatePage( CodeProportion codeProportion ) {
        IHotspotsPage page = createPage( codeProportion );
        if( page != null ) {
            page.setInput( codeProportion );
            showPage( page );
        } else {
            showPage( defaultPage );
        }
    }
}
