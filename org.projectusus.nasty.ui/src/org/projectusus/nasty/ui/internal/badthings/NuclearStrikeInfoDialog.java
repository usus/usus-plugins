// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.nasty.ui.internal.badthings;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

class NuclearStrikeInfoDialog extends SpectacularDialog {

    private final String targetName;
    private Label infoLabel;
    private int counter = -1;

    NuclearStrikeInfoDialog( String targetName ) {
        this.targetName = targetName;
        updateLabelText();
    }

    void setCounterTo( int secondsToImpact ) {
        counter = secondsToImpact;
        updateLabelText();
    }

    @Override
    public void createContents( Composite parent ) {
        infoLabel = new Label( parent, SWT.NONE );
        infoLabel.setText( "|\n|\n|" );
        infoLabel.setForeground( Display.getCurrent().getSystemColor( SWT.COLOR_WHITE ) );
    }

    @Override
    public String getTitle() {
        return "Nuclear strike detected";
    }

    private void updateLabelText() {
        if( infoLabel != null && !infoLabel.isDisposed() ) {
            infoLabel.setText( getInfoText() );
            infoLabel.getParent().layout();
        }
    }

    private String getInfoText() {
        String result = "Target: " + targetName;
        if( counter > 0 ) {
            result += "\n" + String.valueOf( counter ) + " seconds to impact";
        }
        return result;
    }
}
