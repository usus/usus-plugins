// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.projectsettings.ui.internal;

import java.util.List;

import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.projectusus.core.projectsettings.ProjectSettings;
import org.projectusus.core.projectsettings.SettingsProviderExtension;

public class SettingsProviderSelector {

    private final Shell shell;

    public SettingsProviderSelector( Shell shell ) {
        super();
        this.shell = shell;
    }

    public ProjectSettings selectSetting() {
        List<ProjectSettings> settings = new SettingsProviderExtension().loadSettings();
        ImportSettingsWizard wizard = new ImportSettingsWizard( settings );
        WizardDialog dialog = new WizardDialog( shell, wizard );
        dialog.create();
        dialog.open();

        if( dialog.getReturnCode() == Window.OK ) {
            return wizard.getSelectedSetting();
        }
        return null;
    }
}
