// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.projectsettings.core;

import java.util.List;

import org.eclipse.core.resources.IProject;

public class SettingsAccess {

    public void transferSettingsFromProject( List<IProject> projects, IProject source, WhichPrefs[] whichPrefs ) {
        ProjectPreferences projectPreferences = new ProjectPreferences( source );
        for( IProject project : projects ) {
            save( project, projectPreferences, whichPrefs );
        }
    }

    public void applySettings( List<IProject> projects, Preferences settings ) {
        if( settings == null ) {
            return;
        }
        for( IProject project : projects ) {
            save( project, settings, new WhichPrefs[] { WhichPrefs.All } );
        }
    }

    private void save( IProject project, Preferences settings, WhichPrefs[] whichPrefs ) {
        ProjectPreferences projectProperties = new ProjectPreferences( project );
        for( WhichPrefs prefs : whichPrefs ) {
            switch( prefs ) {
            case All:
                projectProperties.updateFrom( settings.getAll() );
                break;
            case CodeCompletion:
                projectProperties.updateFrom( settings.getCodeCompletePrefs() );
                break;
            case CompilerWarnings:
                projectProperties.updateFrom( settings.getCompilerWarningsPrefs() );
                break;
            case Formatting:
                projectProperties.updateFrom( settings.getFormattingPrefs() );
                break;
            default:
                break;
            }
        }
        projectProperties.persist();
    }

}
