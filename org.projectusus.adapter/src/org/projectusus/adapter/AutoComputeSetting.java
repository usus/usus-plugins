// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.adapter;

import static org.eclipse.jdt.core.JavaCore.addPreProcessingResourceChangedListener;
import static org.eclipse.jdt.core.JavaCore.removePreProcessingResourceChangedListener;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;

public class AutoComputeSetting {
    private final IResourceChangeListener resourcelistener = new RunComputationOnResourceChange();

    public AutoComputeSetting() {
        applyAutoCompute( UsusAdapterPlugin.getDefault().getAutocompute() );
    }

    public void setAutoCompute( boolean autoCompute ) {
        UsusAdapterPlugin.getDefault().setAutoCompute( autoCompute );
        applyAutoCompute( autoCompute );
    }

    public void dispose() {
        removePreProcessingResourceChangedListener( resourcelistener );
    }

    private void applyAutoCompute( boolean autoCompute ) {
        if( autoCompute ) {
            addPreProcessingResourceChangedListener( resourcelistener, IResourceChangeEvent.POST_BUILD );
            new ForcedRecompute().schedule();
        } else {
            removePreProcessingResourceChangedListener( resourcelistener );
        }
    }

}
