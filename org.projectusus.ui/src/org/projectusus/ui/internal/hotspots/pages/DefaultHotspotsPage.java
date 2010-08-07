// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.hotspots.pages;

import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.Page;
import org.projectusus.ui.internal.AnalysisDisplayEntry;
import org.projectusus.ui.internal.hotspots.NullSelectionProvider;

public class DefaultHotspotsPage extends Page implements IHotspotsPage {

    private Label control;
    private final ISelectionProvider selectionProvider = new NullSelectionProvider();

    @Override
    public void createControl( Composite parent ) {
        control = new Label( parent, SWT.NONE );
        control.setText( getInfoText() );
    }

    @Override
    public Control getControl() {
        return control;
    }

    @Override
    public void setFocus() {
        if( control != null && !control.isDisposed() ) {
            control.setFocus();
        }
    }

    public void setInput( @SuppressWarnings( "unused" ) AnalysisDisplayEntry element ) {
        // nothing to do on the default page
    }

    public boolean isInitialized() {
        return control != null;
    }

    private String getInfoText() {
        return "No content to display at this time.\nSelect an entry on the Cockpit view and choose 'Open Hotspots'."; //$NON-NLS-1$
    }

    public void refresh() {
        // nothing to refresh
    }

    public boolean matches( @SuppressWarnings( "unused" ) AnalysisDisplayEntry entry ) {
        return false;
    }

    public void resetSort() {
        // do nothing
    }

    public ISelectionProvider getSelectionProvider() {
        return selectionProvider;
    }

}
