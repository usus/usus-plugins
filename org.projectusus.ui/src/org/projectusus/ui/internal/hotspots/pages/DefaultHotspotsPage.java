// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.hotspots.pages;

import static org.projectusus.ui.internal.util.UITexts.defaultHotspotsPage_info;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.Page;
import org.projectusus.core.internal.proportions.model.CodeProportion;

public class DefaultHotspotsPage extends Page implements IHotspotsPage {

    private Label control;

    @Override
    public void createControl( Composite parent ) {
        control = new Label( parent, SWT.NONE );
        control.setText( defaultHotspotsPage_info );
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

    public void setInput( CodeProportion element ) {
        // nothing to do on the default page
    }

    public boolean isInitialized() {
        return control != null;
    }
}