package org.projectusus.adapter;

import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;

class RunComputationOnPreferencesChange implements IPreferenceChangeListener {

    public void preferenceChange( PreferenceChangeEvent event ) {
        IProject project = getWorkspace().getRoot().getProject( event.getNode().name() );
        new ForcedRecompute( project ).schedule();
    }

}
