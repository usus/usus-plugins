// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.projectsettings.ui.internal;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.wizard.Wizard;

public class UnifyProjectSettingsWizard extends Wizard {

    private List<IProject> projects;
    private SelectProjectPage selectProjectPage;

    public UnifyProjectSettingsWizard( List<IProject> projects ) {
        super();
        this.projects = projects;
    }

    @Override
    public void addPages() {
        selectProjectPage = new SelectProjectPage( projects );
        addPage( selectProjectPage );
    }

    @Override
    public boolean performFinish() {
        return selectProjectPage.isProjectSelected();
    }

    public IProject getSelectedProject() {
        return selectProjectPage.getSelectedProject();
    }

}
