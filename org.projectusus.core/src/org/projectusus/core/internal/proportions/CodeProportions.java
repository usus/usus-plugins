// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

import static java.util.Collections.sort;
import static java.util.Collections.unmodifiableList;
import static org.projectusus.core.internal.proportions.IsisMetrics.TA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.projectusus.core.internal.coverage.ICoverageListener;
import org.projectusus.core.internal.coverage.TestCoverage;
import org.projectusus.core.internal.coverage.emmadriver.EmmaDriver;
import org.projectusus.core.internal.proportions.checkpoints.Checkpoints;
import org.projectusus.core.internal.proportions.checkpoints.ICheckpoint;

public class CodeProportions implements ICodeProportions {

    private static CodeProportions _instance;

    private final Map<IsisMetrics, CodeProportion> isisMetricsValues;
    private final EmmaDriver emmaDriver;
    private final ICoverageListener coverageListener;
    private final Set<ICodeProportionsListener> listeners;

    private final CodeProportionsStatus status;

    private final Checkpoints checkpoints;

    private CodeProportions() {
        isisMetricsValues = new HashMap<IsisMetrics, CodeProportion>();
        coverageListener = createCoverageListener();
        listeners = new HashSet<ICodeProportionsListener>();
        status = new CodeProportionsStatus();
        emmaDriver = new EmmaDriver();
        emmaDriver.addCoverageListener( coverageListener );
        checkpoints = new Checkpoints();
        checkpoints.connect( this );
    }

    public static synchronized ICodeProportions getInstance() {
        if( _instance == null ) {
            _instance = new CodeProportions();
        }
        return _instance;
    }

    public List<ICheckpoint> getCheckpoints() {
        return unmodifiableList( checkpoints.getCheckpoints() );
    }

    public List<CodeProportion> getEntries() {
        List<CodeProportion> result = new ArrayList<CodeProportion>();
        result.addAll( isisMetricsValues.values() );
        sort( result, new ByIsisMetricsComparator() );
        return result;
    }

    public ICodeProportionsStatus getLastStatus() {
        return status;
    }

    public void addCodeProportionsListener( ICodeProportionsListener listener ) {
        listeners.add( listener );
    }

    public void removeCodeProportionsListener( ICodeProportionsListener listener ) {
        listeners.remove( listener );
    }

    public void forceRecompute() {
        new CodeProportionsComputerJob().schedule();
    }

    public synchronized void dispose() {
        emmaDriver.removeCoverageListener( coverageListener );
        emmaDriver.dispose();
        isisMetricsValues.clear();
        _instance = null;
    }

    public void updateLastComputerRun() {
        updateLastComputerRun( true );
    }

    void add( CodeProportion proportion ) {
        isisMetricsValues.put( proportion.getMetric(), proportion );
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
        ICodeProportionsStatus lastStatus = getLastStatus();
        List<CodeProportion> entries = getEntries();
        for( ICodeProportionsListener listener : listeners ) {
            listener.codeProportionsChanged( lastStatus, entries );
        }
    }
}
