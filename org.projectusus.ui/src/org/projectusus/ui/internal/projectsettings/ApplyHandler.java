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
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.osgi.service.prefs.BackingStoreException;
import org.projectusus.core.projectsettings.CompilerWarningSetting;
import org.projectusus.core.projectsettings.SettingsProvider;
import org.projectusus.core.projectsettings.SettingsProviderExtension;
import org.projectusus.core.projectsettings.ProjectSettings;

public class ApplyHandler extends AbstractHandler {

    public Object execute( ExecutionEvent event ) throws ExecutionException {
        List<IProject> projects = getSelectedProjects( event );
        for( IProject project : projects ) {
            applySettings( project );
        }
        return null;
    }

    private void applySettings( IProject project ) {
        IEclipsePreferences jdtPrefercences = getJdtPreferences( project );

        ProjectSettings ususSettings = selectSetting().getUsusProjectSettings();
        List<CompilerWarningSetting> settings = ususSettings.getCompilerwarningSettings().getSettings();
        for( CompilerWarningSetting setting : settings ) {
            jdtPrefercences.put( setting.getCode().getSetting(), setting.getValue().name() );

        }
        try {
            jdtPrefercences.flush();
        } catch( BackingStoreException exception ) {
            exception.printStackTrace();
        }
    }

    private SettingsProvider selectSetting() {
        return new SettingsProviderExtension().loadSettingsProvider().get( 0 );
    }

    private IEclipsePreferences getJdtPreferences( IProject project ) {
        ProjectScope projectScope = new ProjectScope( project );
        return projectScope.getNode( "org.eclipse.jdt.core" ); //$NON-NLS-1$
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
