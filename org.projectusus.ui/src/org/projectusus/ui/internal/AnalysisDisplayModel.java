package org.projectusus.ui.internal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.projectusus.core.IUsusModelListener;
import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.statistics.UsusModelProvider;

public class AnalysisDisplayModel {

    private static AnalysisDisplayModel instance;

    private final Set<IDisplayModelListener> listeners;

    private Snapshot snapshot = new Snapshot();

    private IUsusModelListener listener;

    private final DisplayCategories displayCategories;

    public static AnalysisDisplayModel displayModel() {
        if( instance == null ) {
            instance = new AnalysisDisplayModel();
        }
        return instance;
    }

    private AnalysisDisplayModel() {
        super();
        listeners = new HashSet<IDisplayModelListener>();
        displayCategories = new DisplayCategories();
        initModelListener();
    }

    public Snapshot getSnapshot() {
        return snapshot;
    }

    private void initModelListener() {
        listener = new IUsusModelListener() {
            public void ususModelChanged() {
                handleUsusModelChanged();
            }
        };
        UsusModelProvider.ususModel().addUsusModelListener( listener );
    }

    protected void handleUsusModelChanged() {
        displayCategories.replaceCategories( createMetricsCategory() );
        fireUpdateCategories();
    }

    public IDisplayCategory[] getCategories() {
        return displayCategories.getCategories();
    }

    private MetricStatisticsCategory createMetricsCategory() {
        List<CodeProportion> codeProportions = UsusModelProvider.ususModel().getCodeProportions();
        List<AnalysisDisplayEntry> result = new ArrayList<AnalysisDisplayEntry>();
        for( CodeProportion codeProportion : codeProportions ) {
            result.add( displayEntryFor( codeProportion ) );
        }
        return new MetricStatisticsCategory( result.toArray( new AnalysisDisplayEntry[result.size()] ) );
    }

    private AnalysisDisplayEntry displayEntryFor( CodeProportion codeProportion ) {
        displayCategories.getAllEntries();
        for( AnalysisDisplayEntry entry : displayCategories.getAllEntries() ) {
            if( entry.matches( codeProportion ) ) {
                entry.setCodeProportion( codeProportion );
                return entry;
            }
        }
        return new AnalysisDisplayEntry( codeProportion );
    }

    public List<AnalysisDisplayEntry> getEntriesOfAllCategories() {
        return displayCategories.getAllEntries();
    }

    public void createSnapshot() {
        for( AnalysisDisplayEntry entry : getEntriesOfAllCategories() ) {
            entry.createSnapshot();
        }
        snapshot = new Snapshot();
        fireSnapshotCreated();
    }

    public void addModelListener( IDisplayModelListener listen ) {
        listeners.add( listen );
    }

    public void removeModelListener( IDisplayModelListener listen ) {
        listeners.remove( listen );
    }

    // internal
    // ////////

    private void fireUpdateCategories() {
        for( IDisplayModelListener listen : listeners ) {
            listen.updateCategories( this );
        }
    }

    private void fireSnapshotCreated() {
        for( IDisplayModelListener listen : listeners ) {
            listen.snapshotCreated( snapshot );
            listen.updateCategories( this );
        }
    }

}
