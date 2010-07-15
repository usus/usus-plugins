// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.hotspots.pages;

import static java.util.Arrays.asList;

import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.part.Page;
import org.projectusus.core.basis.Hotspot;
import org.projectusus.ui.internal.hotspots.actions.OpenHotspotInEditor;
import org.projectusus.ui.internal.proportions.cockpit.AnalysisDisplayEntry;
import org.projectusus.ui.internal.selection.ExtractHotspot;
import org.projectusus.ui.viewer.UsusTreeViewer;

public class HotspotsPage extends Page implements IHotspotsPage {

    protected UsusTreeViewer<Hotspot> viewer;
    private final IHotspotsPageColumnDesc[] columnDescs;
    private final AnalysisDisplayEntry entry;

    public HotspotsPage( AnalysisDisplayEntry entry ) {
        super();
        this.entry = entry;
        this.columnDescs = HotspotsColumnDesc.values();
    }

    public boolean isInitialized() {
        return viewer != null;
    }

    protected IStructuredContentProvider createContentProvider() {
        return new HotspotsCP();
    }

    private void createViewer( Composite parent ) {
        viewer = new UsusTreeViewer<Hotspot>( parent, columnDescs );
        viewer.setLabelProvider( new HotspotsLP( asList( columnDescs ) ) );
        viewer.setContentProvider( createContentProvider() );
    }

    protected void initOpenListener() {
        viewer.addOpenListener( new IOpenListener() {
            public void open( OpenEvent event ) {
                Hotspot hotspot = new ExtractHotspot( event.getSelection() ).compute();
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
