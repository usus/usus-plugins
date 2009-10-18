// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.projectsettings;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;
import org.projectusus.core.projectsettings.ProjectSettings;
import org.projectusus.core.projectsettings.SettingsAccess;
import org.projectusus.core.projectsettings.SettingsProvider;

public class ApplyProjectSettings extends AbstractHandler {

    public Object execute( ExecutionEvent event ) throws ExecutionException {
        List<IProject> projects = new ProjectSelectionExtractor( event ).getSelectedProjects();
        SettingsProvider settingsProvider = selectSettingsProvider( event );
        saveSettings( settingsProvider, projects );
        return null;
    }

    private SettingsProvider selectSettingsProvider( ExecutionEvent event ) throws ExecutionException {
        Shell shell = HandlerUtil.getActiveWorkbenchWindowChecked( event ).getShell();
        SettingsProvider settingsProvider = new SettingsProviderSelector( shell ).selectSetting();
        return settingsProvider;
    }

    private void saveSettings( SettingsProvider settingsProvider, List<IProject> projects ) {
        if( settingsProvider != null ) {
            ProjectSettings settings = settingsProvider.getUsusProjectSettings();
            new SettingsAccess().save( projects, settings );
        }
    }

}
