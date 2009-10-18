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

public class CopySettingsFromProject extends AbstractHandler {

    private final SettingsAccess settingsAccess = new SettingsAccess();

    public Object execute( ExecutionEvent event ) throws ExecutionException {
        List<IProject> projects = new ProjectSelectionExtractor( event ).getSelectedProjects();
        IProject project = selectProject( event, projects );
        if( project != null ) {
            ProjectSettings settings = loadSettings( project );
            // Don't save settings for selected project.
            projects.remove( project );
            saveSettings( settings, projects );
        }
        return null;
    }

    private IProject selectProject( ExecutionEvent event, List<IProject> projects ) throws ExecutionException {
        Shell shell = HandlerUtil.getActiveWorkbenchWindowChecked( event ).getShell();
        return new ProjectSelector( shell ).selectProject( projects );
    }

    private ProjectSettings loadSettings( IProject project ) {
        return settingsAccess.loadSettings( project );
    }

    private void saveSettings( ProjectSettings settings, List<IProject> projects ) {
        settingsAccess.save( projects, settings );
    }

}
