// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

import static java.util.Collections.sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.projectusus.core.internal.coverage.emmadriver.EclEmmaListener;
import org.projectusus.core.internal.coverage.emmadriver.ICoverageListener;

import com.mountainminds.eclemma.core.CoverageTools;

public class CodeProportions {

    private static CodeProportions _instance;

    private final Map<IsisMetrics, CodeProportion> isisMetricsValues;
    private final EclEmmaListener eclEmmaListener;
    private final ICoverageListener coverageListener;
    private final Set<ICodeProportionsListener> listeners;

    private final CodeProportionsStatus status;

    private CodeProportions() {
        isisMetricsValues = new HashMap<IsisMetrics, CodeProportion>();
        coverageListener = createCoverageListener();
        listeners = new HashSet<ICodeProportionsListener>();
        status = new CodeProportionsStatus();
        eclEmmaListener = new EclEmmaListener();
        CoverageTools.addJavaCoverageListener( eclEmmaListener );
        eclEmmaListener.addCoverageListener( coverageListener );
    }

    public static synchronized CodeProportions getInstance() {
        if( _instance == null ) {
            _instance = new CodeProportions();
        }
        return _instance;
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

    public void add( CodeProportion proportion ) {
        isisMetricsValues.put( proportion.getMetric(), proportion );
    }

    public void updateLastComputerRun() {
        updateLastComputerRun( true );
    }

    public void updateLastComputerRun( boolean successful ) {
        status.setLastComputationRunSuccessful( successful );
        status.updateLastComputerRun();
        notifyListeners();
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
        eclEmmaListener.removeCoverageListener( coverageListener );
        CoverageTools.removeJavaCoverageListener( eclEmmaListener );
        isisMetricsValues.clear();
        _instance = null;
    }

    private ICoverageListener createCoverageListener() {
        return new ICoverageListener() {
            public void coverageChanged() {
                status.updateLastTestRun();
                notifyListeners();
            }
        };
    }

    private void notifyListeners() {
        for( ICodeProportionsListener listener : listeners ) {
            listener.codeProportionsChanged();
        }
    }
}
