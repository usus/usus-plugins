// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.projectsettings.ui.internal;

import java.util.List;

import org.eclipse.jface.wizard.Wizard;
import org.projectusus.core.projectsettings.ProjectSettings;

public class ImportSettingsWizard extends Wizard {

    private final List<ProjectSettings> projectSettings;
    private SelectSettingPage settingsPage;
    private ProjectSettings selectedSetting;

    public ProjectSettings getSelectedSetting() {
        return selectedSetting;
    }

    public ImportSettingsWizard( List<ProjectSettings> projectSettings ) {
        this.projectSettings = projectSettings;
    }

    @Override
    public void addPages() {
        settingsPage = new SelectSettingPage( projectSettings );
        addPage( settingsPage );
    }

    @Override
    public boolean performFinish() {
        selectedSetting = settingsPage.getSelectedSetting();
        return true;
    }

    @Override
    public boolean canFinish() {
        return settingsPage.isProjectSelected();
    }

}
