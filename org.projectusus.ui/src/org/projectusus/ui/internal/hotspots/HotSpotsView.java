// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.hotspots;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.part.ViewPart;
import org.projectusus.ui.internal.AnalysisDisplayEntry;
import org.projectusus.ui.internal.hotspots.pages.DefaultHotspotsPage;
import org.projectusus.ui.internal.hotspots.pages.HotspotsPage;
import org.projectusus.ui.internal.hotspots.pages.IHotspotsPage;

public class HotSpotsView extends ViewPart {

    private PageBook book;
    private IHotspotsPage defaultPage;
    private IHotspotsPage activePage;
    private final Set<IHotspotsPage> pages = new HashSet<IHotspotsPage>();
    private final DelegatingSelectionProvider selectionProvider = new DelegatingSelectionProvider();

    public void update( AnalysisDisplayEntry entry ) {
        if( entry != null ) {
            updatePage( entry );
        }
    }

    @Override
    public void createPartControl( Composite parent ) {
        getSite().setSelectionProvider( selectionProvider );
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
        if( activePage == page ) {
            return;
        }
        activePage = page;
        Control pageControl = activePage.getControl();
        if( pageControl != null && !pageControl.isDisposed() ) {
            // Verify that the page control is not disposed
            // If we are closing, it may have already been disposed
            setContentDescription( page.getDescription() );
            selectionProvider.switchTo( page.getSelectionProvider() );
            book.showPage( pageControl );
        }
    }

    private IHotspotsPage createPage( AnalysisDisplayEntry entry ) {
        IHotspotsPage page = getPageFor( entry );
        if( page != null && !page.isInitialized() ) {
            page.createControl( book );
        }
        return page;
    }

    private IHotspotsPage getPageFor( AnalysisDisplayEntry entry ) {
        if( !entry.hasHotspots() ) {
            return null;
        }
        for( IHotspotsPage page : pages ) {
            if( page.matches( entry ) ) {
                return page;
            }
        }
        HotspotsPage page = new HotspotsPage( entry );
        pages.add( page );
        return page;
    }

    private void updatePage( AnalysisDisplayEntry entry ) {
        IHotspotsPage page = createPage( entry );
        if( page != null ) {
            page.setInput( entry );
            showPage( page );
        } else {
            showPage( defaultPage );
        }
    }

    public void refreshActivePage( List<AnalysisDisplayEntry> entries ) {
        for( AnalysisDisplayEntry entry : entries ) {
            if( activePage != null ) {
                if( activePage.matches( entry ) ) {
                    activePage.setInput( entry );
                }
            }
        }
    }

    public void resetSort() {
        activePage.resetSort();
    }
}
