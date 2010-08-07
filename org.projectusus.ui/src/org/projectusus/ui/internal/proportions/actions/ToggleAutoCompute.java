// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.actions;

import org.eclipse.jface.action.Action;
import org.projectusus.adapter.UsusAdapterPlugin;

public class ToggleAutoCompute extends Action {

    public ToggleAutoCompute() {
        super( "Compute automatically", AS_CHECK_BOX ); //$NON-NLS-1$
        init();
    }

    @Override
    public void run() {
        UsusAdapterPlugin.getDefault().setAutoCompute( isChecked() );
    }

    private void init() {
        setChecked( UsusAdapterPlugin.getDefault().getAutocompute() );
    }
}
