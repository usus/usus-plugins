// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.projectsettings.ui.internal;

import static org.eclipse.ui.handlers.HandlerUtil.getActiveWorkbenchWindowChecked;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.projectusus.projectsettings.core.SettingsAccess;

public class CopySettingsFromProject extends AbstractHandler {

    private final SettingsAccess settingsAccess = new SettingsAccess();

    public Object execute( ExecutionEvent event ) throws ExecutionException {
        List<IProject> projects = new ProjectSelectionExtractor( event ).getSelectedProjects();
        ProjectSelectorResult projectAndChoice = selectProject( event, projects );
        if( projectAndChoice != null ) {
            IProject project = projectAndChoice.getProject();
            projects.remove( project );
            settingsAccess.transferSettingsFromProject( projects, project, projectAndChoice.getWhichPrefs() );
        }
        return null;
    }

    private ProjectSelectorResult selectProject( ExecutionEvent event, List<IProject> projects ) throws ExecutionException {
        return new ProjectSelector( getActiveWorkbenchWindowChecked( event ).getShell() ).selectProject( projects );
    }

}
