package org.projectusus.adapter;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.INodeChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.NodeChangeEvent;

class RegisterPreferenceListenerForAllChildren implements INodeChangeListener {

    private final IPreferenceChangeListener preferenceChangeListener;

    RegisterPreferenceListenerForAllChildren( IPreferenceChangeListener preferenceChangeListener ) {
        this.preferenceChangeListener = preferenceChangeListener;
    }

    public void removed( NodeChangeEvent event ) {
        IEclipsePreferences preferences = (IEclipsePreferences)event.getChild();
        preferences.removePreferenceChangeListener( preferenceChangeListener );
    }

    public void added( NodeChangeEvent event ) {
        IEclipsePreferences preferences = (IEclipsePreferences)event.getChild();
        preferences.addPreferenceChangeListener( preferenceChangeListener );
    }
}
