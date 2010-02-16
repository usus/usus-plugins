// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.coveredprojects;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class TestSuiteSelectionAction extends Action {
    private final Shell shell;

    public TestSuiteSelectionAction( Shell shell ) {
        this.shell = shell;
    }

    @Override
    public void run() {
        MessageDialog.openInformation( shell, "You wonder...", "Not yet implemented." );
    }
}
