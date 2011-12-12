// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.adapter;

import static org.eclipse.jdt.core.JavaCore.addPreProcessingResourceChangedListener;
import static org.eclipse.jdt.core.JavaCore.removePreProcessingResourceChangedListener;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.INodeChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.projectusus.core.UsusCorePlugin;
import org.projectusus.core.project2.IUSUSProject;

public class AutoComputeSetting {

    private final IResourceChangeListener resourcelistener = new RunComputationOnResourceChange();
    private final IPreferenceChangeListener preferenceChangeListener = new RunComputationOnPreferencesChange();
    private final INodeChangeListener nodeListener = new RegisterPreferenceListenerForAllChildren( preferenceChangeListener );

    public AutoComputeSetting() {
        applyAutoCompute( UsusAdapterPlugin.getDefault().getAutocompute() );
    }

    public void setAutoCompute( boolean autoCompute ) {
        applyAutoCompute( autoCompute );
    }

    public void dispose() {
        removePreProcessingResourceChangedListener( resourcelistener );
    }

    private void applyAutoCompute( boolean autoCompute ) {
        if( autoCompute ) {
            addPreProcessingResourceChangedListener( resourcelistener, IResourceChangeEvent.POST_BUILD );
            hookPreferenceChanges();
        } else {
            removePreProcessingResourceChangedListener( resourcelistener );
            unhookPreferenceChanges();
        }
    }

    private void hookPreferenceChanges() {
        IEclipsePreferences preferences = getUsusProjectPreferences();
        preferences.addNodeChangeListener( nodeListener );
        for( IEclipsePreferences projectPreferences : collectChildren( preferences ) ) {
            projectPreferences.addPreferenceChangeListener( preferenceChangeListener );
        }
    }

    private void unhookPreferenceChanges() {
        IEclipsePreferences preferences = getUsusProjectPreferences();
        preferences.removeNodeChangeListener( nodeListener );
        for( IEclipsePreferences projectPreferences : collectChildren( preferences ) ) {
            projectPreferences.removePreferenceChangeListener( preferenceChangeListener );
        }
    }

    private Iterable<IEclipsePreferences> collectChildren( IEclipsePreferences preferences ) {
        List<IEclipsePreferences> children = new LinkedList<IEclipsePreferences>();
        try {
            for( String childName : preferences.childrenNames() ) {
                IEclipsePreferences child = (IEclipsePreferences)preferences.node( childName );
                children.add( child );
            }
        } catch( Exception exception ) {
            log( exception );
        }
        return children;
    }

    private void log( Exception exception ) {
        Status status = new Status( IStatus.ERROR, UsusAdapterPlugin.PLUGIN_ID, "Unable to read child names of Usus Project Preferences", exception ); //$NON-NLS-1$
        UsusAdapterPlugin.getDefault().getLog().log( status );
    }

    private IEclipsePreferences getUsusProjectPreferences() {
        return (IEclipsePreferences)UsusCorePlugin.getDefault().getPreferences().node( IUSUSProject.ATT_USUS_PROJECT );
    }
}
