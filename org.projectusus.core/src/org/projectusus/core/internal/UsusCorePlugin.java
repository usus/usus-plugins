// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal;

import static org.eclipse.core.runtime.IStatus.ERROR;
import static org.projectusus.core.internal.util.UsusPreferenceKeys.AUTO_COMPUTE;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.service.prefs.BackingStoreException;
import org.projectusus.core.filerelations.FileRelationMetrics;
import org.projectusus.core.internal.coverage.LaunchObserver;
import org.projectusus.core.internal.proportions.IUsusModelMetricsWriter;
import org.projectusus.core.internal.proportions.IUsusModelWriteAccess;
import org.projectusus.core.internal.proportions.rawdata.IUsusModel;
import org.projectusus.core.internal.proportions.rawdata.NullUsusModelMetricsWriter;
import org.projectusus.core.internal.proportions.rawdata.NullUsusModelWriteAccess;
import org.projectusus.core.internal.proportions.rawdata.UsusModel;

public class UsusCorePlugin extends Plugin {

    // using hard-coded string instead of bundle symbolic name, since we
    // cannot rely on the bundle still being there during shutdown when
    // some jobs still run
    public static final String PLUGIN_ID = "org.projectusus.core"; //$NON-NLS-1$
    private static UsusCorePlugin plugin;
    private final LaunchObserver launchObserver = new LaunchObserver();
    private UsusModel ususModel;

    public static UsusCorePlugin getDefault() {
        return plugin;
    }

    public static synchronized IUsusModel getUsusModel() {
        return getDefault().ususModel;
    }

    public static synchronized IUsusModelWriteAccess getUsusModelWriteAccess() {
        UsusCorePlugin ususCorePlugin = UsusCorePlugin.getDefault();
        // inside a background job, the plugin might have been
        // shut down meanwhile
        if( ususCorePlugin != null ) {
            return ususCorePlugin.ususModel;
        }
        return new NullUsusModelWriteAccess();
    }

    public static synchronized IUsusModelMetricsWriter getUsusModelMetricsWriter() {
        UsusCorePlugin ususCorePlugin = UsusCorePlugin.getDefault();
        // inside a background job, the plugin might have been
        // shut down meanwhile
        if( ususCorePlugin != null ) {
            return ususCorePlugin.ususModel;
        }
        return new NullUsusModelMetricsWriter();
    }

    public static void log( Exception ex ) {
        String msg = ex.getMessage() == null ? "[No details.]" : ex.getMessage(); //$NON-NLS-1$
        log( msg, ex );
    }

    public static void log( String msg, Exception ex ) {
        if( getDefault() != null ) {
            IStatus status = new Status( ERROR, PLUGIN_ID, 0, msg, ex );
            getDefault().getLog().log( status );
        }
    }

    private IEclipsePreferences getPreferences() {
        return new InstanceScope().getNode( PLUGIN_ID );
    }

    @Override
    public void start( final BundleContext context ) throws Exception {
        super.start( context );
        plugin = this;
        ususModel = new UsusModel();
        context.addBundleListener( new BundleListener() {
            public void bundleChanged( BundleEvent event ) {
                if( event.getType() == BundleEvent.STARTED ) {
                    context.removeBundleListener( this );
                    performInits();
                }
            }
        } );
    }

    @Override
    public void stop( BundleContext context ) throws Exception {
        launchObserver.dispose();
        plugin = null;
        ususModel = null;
        super.stop( context );
    }

    private synchronized void performInits() {
        launchObserver.connect();
    }

    public boolean getAutocompute() {
        return getPreferences().getBoolean( AUTO_COMPUTE, true );
    }

    public void setAutoCompute( boolean autoCompute ) {
        try {
            IEclipsePreferences prefs = getPreferences();
            prefs.putBoolean( AUTO_COMPUTE, autoCompute );
            prefs.flush();
        } catch( BackingStoreException bastox ) {
            UsusCorePlugin.log( bastox );
        }
    }

    /**
     * @deprecated will soon be deleted; access to FileRelations via Descriptors then
     */
    public static FileRelationMetrics getFileRelationMetrics() {
        return getDefault().ususModel.getFileRelationMetrics();
    }

}
