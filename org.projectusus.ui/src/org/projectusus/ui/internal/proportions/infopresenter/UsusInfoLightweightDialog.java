// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.infopresenter;

import static org.eclipse.swt.layout.GridData.FILL_BOTH;
import static org.eclipse.swt.layout.GridData.FILL_HORIZONTAL;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.projectusus.ui.internal.proportions.infopresenter.infomodel.IUsusInfo;
import org.projectusus.ui.internal.viewer.LightweightDialog;

class UsusInfoLightweightDialog extends LightweightDialog {

    private IUsusInfo ususInfo;
    private UsusInfoViewer viewer;

    UsusInfoLightweightDialog( Shell parentShell ) {
        super( parentShell );
    }

    @Override
    public void setInput( Object input ) {
        ususInfo = (IUsusInfo)input;
    }

    @Override
    protected void createTitleArea( Composite area ) {
        Label lblTitle = new Label( area, SWT.NONE );
        lblTitle.setLayoutData( new GridData( FILL_HORIZONTAL ) );
        applyInfoColors( lblTitle );
        makeBold( lblTitle );
        lblTitle.setText( ususInfo.formatTitle() );
    }

    @Override
    protected void createMainArea( Composite area ) {
        viewer = new UsusInfoViewer( area, ususInfo );
        viewer.getControl().setLayoutData( new GridData( FILL_BOTH ) );
        applyInfoColors( viewer.getControl() );
        viewer.expandAll();
    }

    @Override
    protected void setFocus() {
        if( viewer != null && !viewer.getControl().isDisposed() ) {
            viewer.getControl().setFocus();
        }
    }
}
