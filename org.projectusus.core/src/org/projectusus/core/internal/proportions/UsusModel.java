// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

import static java.util.Collections.unmodifiableList;
import static org.projectusus.core.internal.proportions.IsisMetrics.TA;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.projectusus.core.internal.coverage.ICoverageListener;
import org.projectusus.core.internal.coverage.TestCoverage;
import org.projectusus.core.internal.coverage.emmadriver.EmmaDriver;
import org.projectusus.core.internal.proportions.checkpoints.Checkpoints;
import org.projectusus.core.internal.proportions.checkpoints.ICheckpoint;
import org.projectusus.core.internal.proportions.model.IUsusElement;
import org.projectusus.core.internal.proportions.model.UsusModelRootNode;

public class UsusModel implements IUsusModel {

    private static UsusModel _instance;

    private final EmmaDriver emmaDriver;
    private final ICoverageListener coverageListener;
    private final Set<IUsusModelListener> listeners;

    private final UsusModelStatus status;

    private final Checkpoints checkpoints;
    private final UsusModelRootNode rootNode;

    private UsusModel() {
        rootNode = new UsusModelRootNode();
        coverageListener = createCoverageListener();
        listeners = new HashSet<IUsusModelListener>();
        status = new UsusModelStatus();
        emmaDriver = new EmmaDriver();
        emmaDriver.addCoverageListener( coverageListener );
        checkpoints = new Checkpoints();
        checkpoints.connect( this );
    }

    public static synchronized IUsusModel getInstance() {
        if( _instance == null ) {
            _instance = new UsusModel();
        }
        return _instance;
    }

    public List<ICheckpoint> getCheckpoints() {
        return unmodifiableList( checkpoints.getCheckpoints() );
    }

    public IUsusElement[] getElements() {
        return rootNode.getElements();
    }

    public IUsusModelStatus getLastStatus() {
        return status;
    }

    public void addUsusModelListener( IUsusModelListener listener ) {
        listeners.add( listener );
    }

    public void removeUsusModelListener( IUsusModelListener listener ) {
        listeners.remove( listener );
    }

    public void forceRecompute() {
        new CodeProportionsComputerJob().schedule();
    }

    public synchronized void dispose() {
        emmaDriver.removeCoverageListener( coverageListener );
        emmaDriver.dispose();
        _instance = null;
    }

    public void updateLastComputerRun() {
        updateLastComputerRun( true );
    }

    void add( CodeProportion proportion ) {
        rootNode.add( proportion );
    }

    void updateLastComputerRun( boolean successful ) {
        status.setLastComputationRunSuccessful( successful );
        status.updateLastComputerRun();
        notifyListeners();
    }

    private ICoverageListener createCoverageListener() {
        return new ICoverageListener() {
            public void coverageChanged( TestCoverage coverage ) {
                int value = coverage.getCoveredCount();
                add( new CodeProportion( TA, coverage.toString(), value ) );
                status.updateLastTestRun();
                notifyListeners();
            }
        };
    }

    private void notifyListeners() {
        IUsusModelStatus lastStatus = getLastStatus();
        IUsusElement[] elements = getElements();
        for( IUsusElement element : elements ) {
            for( IUsusModelListener listener : listeners ) {
                listener.ususModelChanged( lastStatus, element.getEntries() );
            }
        }

    }
}
