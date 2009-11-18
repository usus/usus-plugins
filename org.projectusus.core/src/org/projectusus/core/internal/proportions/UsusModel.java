// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

import static java.util.Arrays.asList;
import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IResourceChangeListener;
import org.projectusus.core.internal.proportions.model.CodeProportion;
import org.projectusus.core.internal.proportions.model.IUsusElement;
import org.projectusus.core.internal.proportions.model.UsusModelRootNode;
import org.projectusus.core.internal.proportions.modelupdate.IUsusModelHistory;
import org.projectusus.core.internal.proportions.modelupdate.IUsusModelUpdate;
import org.projectusus.core.internal.proportions.modelupdate.UsusModelHistory;

public class UsusModel implements IUsusModel {

    private static UsusModel _instance;

    private final Set<IUsusModelListener> listeners;
    private final UsusModelHistory history;
    private final UsusModelRootNode rootNode;

    private final IResourceChangeListener resourcelistener = new RunComputationOnResourceChange();

    private UsusModel() {
        rootNode = new UsusModelRootNode();
        listeners = new HashSet<IUsusModelListener>();
        history = new UsusModelHistory();
        getWorkspace().addResourceChangeListener( resourcelistener );
    }

    public static synchronized IUsusModel getUsusModel() {
        if( _instance == null ) {
            _instance = new UsusModel();
        }
        return _instance;
    }

    public void update( IUsusModelUpdate updateCommand ) {
        if( updateCommand == null || updateCommand.getType() == null ) {
            throw new IllegalArgumentException();
        }
        doUpdate( updateCommand );
    }

    public synchronized void dispose() {
        getWorkspace().removeResourceChangeListener( resourcelistener );
        _instance = null;
    }

    // interface of IUsusModel
    // ////////////////////////

    public IUsusModelHistory getHistory() {
        return history;
    }

    public IUsusElement[] getElements() {
        return rootNode.getElements();
    }

    public void addUsusModelListener( IUsusModelListener listener ) {
        listeners.add( listener );
    }

    public void removeUsusModelListener( IUsusModelListener listener ) {
        listeners.remove( listener );
    }

    public void forceRecompute() {
        ICodeProportionComputationTarget wsTarget = new WorkspaceCodeProportionComputationTarget();
        new CodeProportionsComputerJob( wsTarget ).schedule();
    }

    // internal
    // /////////

    private void notifyListeners() {
        IUsusElement[] elements = getElements();
        for( IUsusModelListener listener : listeners ) {
            listener.ususModelChanged( history, asList( elements ) );
        }
    }

    private void doUpdate( IUsusModelUpdate updateCommand ) {
        for( CodeProportion entry : updateCommand.getEntries() ) {
            rootNode.add( entry );
        }
        history.add( updateCommand );
        notifyListeners();
    }
}
