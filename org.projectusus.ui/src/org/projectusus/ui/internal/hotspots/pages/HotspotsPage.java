// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.hotspots.pages;

import static java.util.Arrays.asList;

import org.eclipse.core.commands.NotHandledException;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.services.IServiceLocator;
import org.projectusus.core.util.Defect;
import org.projectusus.ui.internal.AnalysisDisplayEntry;
import org.projectusus.ui.internal.DisplayHotspot;
import org.projectusus.ui.internal.hotspots.commands.AbstractOpenHotspotHandler;
import org.projectusus.ui.viewer.UsusTreeViewer;

public class HotspotsPage extends Page implements IHotspotsPage {

    private final IServiceLocator serviceLocator;
    private final AnalysisDisplayEntry entry;

    private UsusTreeViewer<DisplayHotspot<?>> viewer;
    private ViewerComparator comparator;

    public HotspotsPage( IServiceLocator serviceLocator, AnalysisDisplayEntry entry ) {
        super();
        this.serviceLocator = serviceLocator;
        this.entry = entry;
    }

    public boolean isInitialized() {
        return viewer != null;
    }

    protected IStructuredContentProvider createContentProvider() {
        return new HotspotsCP();
    }

    private void createViewer( Composite parent ) {
        HotspotsColumnDesc[] columnDescs = HotspotsColumnDesc.getColumnDescs( entry.getLocationType() );
        viewer = new UsusTreeViewer<DisplayHotspot<?>>( parent, columnDescs );
        viewer.setLabelProvider( new HotspotsLP( asList( columnDescs ) ) );
        viewer.setContentProvider( createContentProvider() );
        comparator = new ViewerComparator() {
            @Override
            public int compare( @SuppressWarnings( "unused" ) Viewer viewer1, Object e1, Object e2 ) {
                return ((DisplayHotspot<?>)e1).compareTo( (DisplayHotspot<?>)e2 );
            }
        };
        viewer.setComparator( comparator );

    }

    protected void initOpenListener() {
        viewer.addOpenListener( new IOpenListener() {
            public void open( @SuppressWarnings( "unused" ) OpenEvent event ) {
                IHandlerService handlerService = (IHandlerService)serviceLocator.getService( IHandlerService.class );
                try {
                    handlerService.executeCommand( AbstractOpenHotspotHandler.COMMAND_ID, null );
                } catch( NotHandledException ignore ) {
                    // to be ignored
                } catch( Exception exception ) {
                    throw new Defect( exception );
                }
            }
        } );
    }

    public void setInput( AnalysisDisplayEntry entry ) {
        TreeSelection selection = (TreeSelection)viewer.getSelection();
        viewer.setInput( entry );
        if( !selection.isEmpty() ) {
            Object selectedDisplayHotspot = selection.getFirstElement();
            if( selectedDisplayHotspot instanceof DisplayHotspot<?> ) {
                selectCodeProportionInTree( (DisplayHotspot<?>)selectedDisplayHotspot );
            }
        }
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

    public void refresh() {
        setInput( entry );
    }

    public boolean matches( AnalysisDisplayEntry otherEntry ) {
        return entry.isSameKindAs( otherEntry );
    }

    public void resetSort() {
        viewer.setComparator( comparator );
        viewer.resetColumnSorting();
    }

    public ISelectionProvider getSelectionProvider() {
        return viewer;
    }

    public String getDescription() {
        return entry.getDescription();
    }

    private void selectCodeProportionInTree( DisplayHotspot<?> hotspot ) {
        for( DisplayHotspot<?> currrent : entry.getHotspots() ) {
            if( currrent.getCurrentOrOldHotspot().equals( hotspot.getCurrentOrOldHotspot() ) ) {
                viewer.setSelection( createTreeSelection( currrent ) );
                return;
            }
        }
    }

    private TreeSelection createTreeSelection( Object... elements ) {
        return new TreeSelection( new TreePath( elements ) );
    }

}
