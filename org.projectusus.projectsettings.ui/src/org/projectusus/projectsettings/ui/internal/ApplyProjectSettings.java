// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.projectsettings.ui.internal;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;
import org.projectusus.projectsettings.core.Preferences;
import org.projectusus.projectsettings.core.SettingsAccess;

public class ApplyProjectSettings extends AbstractHandler {

    public Object execute( ExecutionEvent event ) throws ExecutionException {
        List<IProject> projects = new ProjectSelectionExtractor( event ).getSelectedProjects();
        Preferences settingsProvider = selectSettingsProvider( event );
        saveSettings( settingsProvider, projects );
        return null;
    }

    private Preferences selectSettingsProvider( ExecutionEvent event ) throws ExecutionException {
        Shell shell = HandlerUtil.getActiveWorkbenchWindowChecked( event ).getShell();
        Preferences selectedProjectSetting = new SettingsProviderSelector( shell ).selectSetting();
        return selectedProjectSetting;
    }

    private void saveSettings( Preferences settings, List<IProject> projects ) {
        new SettingsAccess().applySettings( projects, settings );
    }

}
