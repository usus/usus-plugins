// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.autotestsuite.ui.internal;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

abstract class MassSelectionComposite extends Composite {

    MassSelectionComposite( Composite parent ) {
        super( parent, SWT.NONE );
        initLayout();
        initLayoutData();

        Button selectAllButton = new Button( this, SWT.PUSH );
        selectAllButton.setText( "Select All" );
        selectAllButton.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent evt ) {
                onSelectAll();
            }
        } );
        setButtonGridData( selectAllButton );

        Button deselectAllButton = new Button( this, SWT.PUSH );
        deselectAllButton.setText( "Deselect All" );
        deselectAllButton.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent evt ) {
                onDeselectAll();
            }
        } );
        setButtonGridData( deselectAllButton );
    }

    abstract void onSelectAll();

    abstract void onDeselectAll();

    private void initLayout() {
        GridLayout layout = new GridLayout( 1, false );
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        layout.horizontalSpacing = 0;
        layout.verticalSpacing = 0;
        setLayout( layout );
    }

    private void initLayoutData() {
        GridData data = new GridData();
        data.verticalAlignment = SWT.TOP;
        setLayoutData( data );
    }

    private void setButtonGridData( Button button ) {
        GridData gridData = new GridData();
        gridData.horizontalAlignment = GridData.FILL;
        button.setLayoutData( gridData );
    }
}