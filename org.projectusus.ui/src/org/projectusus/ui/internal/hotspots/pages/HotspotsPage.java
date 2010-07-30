// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.hotspots.pages;

import static java.util.Arrays.asList;

import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.part.Page;
import org.projectusus.ui.internal.AnalysisDisplayEntry;
import org.projectusus.ui.internal.DisplayHotspot;
import org.projectusus.ui.internal.hotspots.actions.OpenHotspotInEditor;
import org.projectusus.ui.internal.selection.ExtractHotspot;
import org.projectusus.ui.viewer.UsusTreeViewer;

public class HotspotsPage extends Page implements IHotspotsPage {

    protected UsusTreeViewer<DisplayHotspot> viewer;
    private final AnalysisDisplayEntry entry;

    public HotspotsPage( AnalysisDisplayEntry entry ) {
        super();
        this.entry = entry;
    }

    public boolean isInitialized() {
        return viewer != null;
    }

    protected IStructuredContentProvider createContentProvider() {
        return new HotspotsCP();
    }

    private void createViewer( Composite parent ) {
        HotspotsColumnDesc[] columnDescs = HotspotsColumnDesc.values();
        viewer = new UsusTreeViewer<DisplayHotspot>( parent, columnDescs );
        viewer.setLabelProvider( new HotspotsLP( asList( columnDescs ) ) );
        viewer.setContentProvider( createContentProvider() );
        ViewerComparator comparator = new ViewerComparator() {
            @Override
            public int compare( Viewer viewer, Object e1, Object e2 ) {
                DisplayHotspot hotspot1 = (DisplayHotspot)e1;
                DisplayHotspot hotspot2 = (DisplayHotspot)e2;
                return hotspot1.getMetricsValue() - hotspot2.getMetricsValue();
            }
        };
        viewer.setComparator( comparator );

    }

    protected void initOpenListener() {
        viewer.addOpenListener( new IOpenListener() {
            public void open( OpenEvent event ) {
                DisplayHotspot hotspot = new ExtractHotspot( event.getSelection() ).compute();
                if( hotspot != null ) {
                    new OpenHotspotInEditor( hotspot ).run();
                }
            }
        } );
    }

    public void setInput( AnalysisDisplayEntry entry ) {
        viewer.setInput( entry );
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
}
