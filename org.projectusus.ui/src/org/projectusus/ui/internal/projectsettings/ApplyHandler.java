// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.projectsettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

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
        Properties preferences = new SettingsLoader().getCompilerWarningsDefaults();
        Set<Object> keySet = preferences.keySet();
        for( Object key : keySet ) {
            jdtPrefercences.put( String.valueOf( key ), String.valueOf( preferences.get( key ) ) );
        }
        try {
            jdtPrefercences.flush();
        } catch( BackingStoreException exception ) {
            exception.printStackTrace();
        }
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
