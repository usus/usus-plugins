// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal;

import static org.eclipse.jdt.core.JavaCore.removePreProcessingResourceChangedListener;
import static org.projectusus.core.internal.util.UsusPreferenceKeys.AUTO_COMPUTE;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jdt.core.JavaCore;
import org.osgi.service.prefs.BackingStoreException;
import org.projectusus.core.internal.proportions.modelcomputation.ForcedRecompute;
import org.projectusus.core.internal.proportions.modelcomputation.RunComputationOnResourceChange;

public class AutoComputeSetting {
    private final IResourceChangeListener resourcelistener = new RunComputationOnResourceChange();

    public AutoComputeSetting() {
        boolean autoCompute = getPrefs().getBoolean( AUTO_COMPUTE, true );
        applyAutoCompute( autoCompute );
    }

    public void setAutoCompute( boolean autoCompute ) {
        try {
            writePref( autoCompute );
        } catch( BackingStoreException bastox ) {
            UsusCorePlugin.log( bastox );
        }
        applyAutoCompute( autoCompute );
    }

    public void dispose() {
        removePreProcessingResourceChangedListener( resourcelistener );
        // getWorkspace().removeResourceChangeListener( resourcelistener );
    }

    private void writePref( boolean autoCompute ) throws BackingStoreException {
        IEclipsePreferences prefs = getPrefs();
        prefs.putBoolean( AUTO_COMPUTE, autoCompute );
        prefs.flush();
    }

    private void applyAutoCompute( boolean autoCompute ) {
        if( autoCompute ) {
            JavaCore.addPreProcessingResourceChangedListener( resourcelistener, IResourceChangeEvent.POST_BUILD );
            // getWorkspace().addResourceChangeListener( resourcelistener );
            new ForcedRecompute().schedule();
        } else {
            removePreProcessingResourceChangedListener( resourcelistener );
            // getWorkspace().removeResourceChangeListener( resourcelistener );
        }
    }

    private IEclipsePreferences getPrefs() {
        return UsusCorePlugin.getDefault().getPreferences();
    }
}
