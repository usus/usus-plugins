// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.projectusus.core.IMetricsAccessor;
import org.projectusus.core.IUsusModel;
import org.projectusus.core.IUsusModelListener;
import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.ClassDescriptorCleanup;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.core.internal.proportions.IMetricsWriter;
import org.projectusus.core.internal.proportions.IUsusModelForAdapter;
import org.projectusus.core.internal.proportions.model.UsusModelCache;

public class UsusModel implements IUsusModel, IUsusModelForAdapter {

    private static UsusModel instance = new UsusModel();

    private final Set<IUsusModelListener> listeners;
    private final UsusModelCache cache;
    private final MetricsAccessor metrics;
    private boolean needsFullRecompute;

    private UsusModel() {
        cache = new UsusModelCache();
        listeners = new HashSet<IUsusModelListener>();
        metrics = new MetricsAccessor();
        needsFullRecompute = false;
    }

    public static UsusModel ususModel() {
        return instance;
    }

    // interface of IUsusModelWriteAccess
    // //////////////////////////////////

    public void updateAfterComputationRun( boolean computationSuccessful, IProgressMonitor monitor ) {
        needsFullRecompute = !computationSuccessful;
        metrics.cleanupRelations( monitor );
        List<CodeProportion> codeProportions = metrics.getCodeProportions();
        cache.refreshAll( codeProportions );
        notifyListeners();
    }

    public void dropRawData( IProject project ) {
        metrics.dropRawData( project );
    }

    public void dropRawData( IFile file ) {
        metrics.dropRawData( file );
    }

    public IMetricsAccessor getMetricsAccessor() {
        return metrics;
    }

    public IMetricsWriter getMetricsWriter() {
        return metrics;
    }

    // interface of IUsusModel
    // ////////////////////////

    public List<CodeProportion> getCodeProportions() {
        return cache.getEntries();
    }

    // //////////////////////////////////

    public void addUsusModelListener( IUsusModelListener listener ) {
        listeners.add( listener );
    }

    public void removeUsusModelListener( IUsusModelListener listener ) {
        listeners.remove( listener );
    }

    // internal
    // ////////

    private void notifyListeners() {
        for( IUsusModelListener listener : listeners ) {
            listener.ususModelChanged();
        }
    }

    public boolean needsFullRecompute() {
        return needsFullRecompute;
    }

    public static void clear() {
        instance = new UsusModel();
        ClassDescriptor.clear();
        ClassDescriptorCleanup.clear();
        Packagename.clear();
    }
}
