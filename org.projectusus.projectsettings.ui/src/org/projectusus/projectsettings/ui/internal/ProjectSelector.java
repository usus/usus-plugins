// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.projectsettings.ui.internal;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

public class ProjectSelector {

    private final Shell shell;

    public ProjectSelector( Shell shell ) {
        this.shell = shell;
    }

    public IProject selectProject( List<IProject> projects ) {
        UnifyProjectSettingsWizard wizard = new UnifyProjectSettingsWizard( projects );

        WizardDialog dialog = new WizardDialog( shell, wizard );
        dialog.create();
        dialog.open();

        if( dialog.getReturnCode() == Window.OK ) {
            return wizard.getSelectedProject();
        }
        return null;

    }
}
