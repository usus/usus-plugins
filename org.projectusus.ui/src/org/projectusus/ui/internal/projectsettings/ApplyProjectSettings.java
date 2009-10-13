// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.projectsettings;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;
import org.projectusus.core.projectsettings.ProjectSettings;
import org.projectusus.core.projectsettings.SettingsAccess;
import org.projectusus.core.projectsettings.SettingsProvider;

public class ApplyProjectSettings extends AbstractHandler {

    public Object execute( ExecutionEvent event ) throws ExecutionException {
        SettingsProvider settingsProvider = selectSettingsProvider( event );
        List<IProject> projects = getSelectedProjects( event );
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

    private List<IProject> getSelectedProjects( ExecutionEvent event ) {
        List<IProject> result = new ArrayList<IProject>();
        ISelection selection = HandlerUtil.getActiveMenuSelection( event );
        if( selection instanceof IStructuredSelection ) {
            IStructuredSelection sselection = (IStructuredSelection)selection;
            List<?> elements = sselection.toList();
            for( Object object : elements ) {
                if( object instanceof IJavaProject ) {
                    IJavaProject javaProject = (IJavaProject)object;
                    result.add( javaProject.getProject() );
                }
            }
        }
        return result;
    }

}
