// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.project;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.osgi.service.prefs.BackingStoreException;
import org.projectusus.core.internal.UsusCorePlugin;
import org.projectusus.core.internal.bugreport.Bug;
import org.projectusus.core.internal.bugreport.BugFileWriter;
import org.projectusus.core.internal.bugreport.BugList;
import org.projectusus.core.internal.proportions.sqi.ProjectResults;
import org.projectusus.core.internal.proportions.sqi.WorkspaceResults;

class UsusProject implements IUSUSProject {

    private static final String BUGS_FILENAME = "bugs.usus"; //$NON-NLS-1$
    private static final String ATT_USUS_PROJECT = "ususProject"; //$NON-NLS-1$
    private final IProject project;

    UsusProject( IProject project ) {
        this.project = project;
    }

    public void setUsusProject( boolean ususProject ) {
        try {
            IEclipsePreferences node = getProjectSettings();
            node.putBoolean( ATT_USUS_PROJECT, ususProject );
            node.flush();
        } catch( BackingStoreException basex ) {
            UsusCorePlugin.log( basex );
        }
    }

    public boolean isUsusProject() {
        return getProjectSettings().getBoolean( ATT_USUS_PROJECT, false );
    }

    // internal
    // /////////

    private IEclipsePreferences getProjectSettings() {
        ProjectScope projectScope = new ProjectScope( project );
        return projectScope.getNode( UsusCorePlugin.getPluginId() );
    }

    public void saveBug( Bug bug ) {
        IFile file = project.getFile( BUGS_FILENAME );
        BugFileWriter bugFileWriter = new BugFileWriter();
        BugList bugList = loadFromFile( file );
        bugList.addBug( bug );
        bugFileWriter.writToFile( file, bugList );
    }

    private BugList loadFromFile( IFile file ) {
        BugList bugList;
        if( file.exists() ) {
            bugList = new BugFileWriter().readFromFile( file );
        } else {
            bugList = new BugList();
        }
        return bugList;
    }

    public BugList getBugs() {
        IFile file = project.getFile( BUGS_FILENAME );
        return loadFromFile( file );
    }

    public ProjectResults getProjectResults() {
        return WorkspaceResults.getInstance().getProjectResults( project );
    }
}
