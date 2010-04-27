// Copyright (c) 2005-2006, 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.yellowcount;

import java.util.ArrayList;
import java.util.List;

import org.projectusus.core.internal.UsusCorePlugin;
import org.projectusus.core.internal.proportions.IUsusModel;
import org.projectusus.core.internal.proportions.IUsusModelListener;
import org.projectusus.core.internal.proportions.model.CodeProportion;
import org.projectusus.core.internal.proportions.rawdata.CheckpointHistory;
import org.projectusus.core.internal.proportions.rawdata.CodeProportionKind;
import org.projectusus.core.internal.proportions.rawdata.CodeProportionUnit;

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
        UsusCorePlugin.getUsusModel().addUsusModelListener( new IUsusModelListener() {

            /**
             * @param history
             *            not used in method
             */
            public void ususModelChanged( CheckpointHistory history ) {
                notifyListeners();
            }
        } );
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
        IUsusModel ususModel = UsusCorePlugin.getUsusModel();
        return createResult( ususModel.getCodeProportion( CodeProportionKind.CW ), ususModel.getNumberOf( CodeProportionUnit.PROJECT ), ususModel.getNumberOfProjectsViolatingCW() );
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
