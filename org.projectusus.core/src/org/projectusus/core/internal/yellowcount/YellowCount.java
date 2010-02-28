// Copyright (c) 2005-2006, 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.yellowcount;

import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.projectusus.core.internal.UsusCorePlugin;
import org.projectusus.core.internal.proportions.model.CodeProportion;
import org.projectusus.core.internal.proportions.rawdata.CodeProportionKind;

/**
 * <p>
 * the central singleton that counts occurences of yellow in the workspace and notifies also about changes.
 * </p>
 * 
 * @author Leif Frenzel
 */
public class YellowCount {

    private static YellowCount _instance;

    private final List<IYellowCountListener> listeners;

    private YellowCount() {
        listeners = new ArrayList<IYellowCountListener>();
        getWorkspace().addResourceChangeListener( new IResourceChangeListener() {
            public void resourceChanged( final IResourceChangeEvent event ) {
                notifyListeners();
            }
        }, IResourceChangeEvent.POST_BUILD | IResourceChangeEvent.POST_CHANGE );
    }

    public static synchronized YellowCount getInstance() {
        if( _instance == null ) {
            _instance = new YellowCount();
        }
        return _instance;
    }

    public void addYellowCountListener( final IYellowCountListener listener ) {
        listeners.add( listener );
    }

    public void removeYellowCountListener( final IYellowCountListener listener ) {
        listeners.remove( listener );
    }

    public IYellowCountResult count() {
        return createResult( UsusCorePlugin.getUsusModel().getCodeProportion( CodeProportionKind.CW ), 0, 0 ); // TODO
    }

    private void notifyListeners() {
        for( IYellowCountListener listener : listeners ) {
            listener.yellowCountChanged();
        }
    }

    private YellowCountResult createResult( CodeProportion codeProportion, int projectCount, int yellowProjectCount ) {
        return new YellowCountResult( projectCount, codeProportion.getBasis().getValue(), codeProportion.getViolations(), yellowProjectCount );
    }
}
