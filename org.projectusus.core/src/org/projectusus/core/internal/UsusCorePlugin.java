// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal;

import static org.eclipse.core.runtime.IStatus.ERROR;
import static org.projectusus.core.internal.proportions.UsusModel.getUsusModel;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.projectusus.core.internal.coverage.LaunchObserver;
import org.projectusus.core.internal.proportions.UsusModel;

public class UsusCorePlugin extends Plugin {

    private static UsusCorePlugin plugin;
    private final LaunchObserver launchObserver = new LaunchObserver();

    public static UsusCorePlugin getDefault() {
        return plugin;
    }

    public static String getPluginId() {
        return getDefault().getBundle().getSymbolicName();
    }

    @Override
    public void start( final BundleContext context ) throws Exception {
        super.start( context );
        plugin = this;
        context.addBundleListener( new BundleListener() {
            public void bundleChanged( BundleEvent event ) {
                if( event.getType() == BundleEvent.STARTED ) {
                    getUsusModel().forceRecompute();
                    launchObserver.connect();
                    context.removeBundleListener( this );
                }
            }
        } );
    }

    @Override
    public void stop( BundleContext context ) throws Exception {
        launchObserver.dispose();
        ((UsusModel)UsusModel.getUsusModel()).dispose();
        plugin = null;
        super.stop( context );
    }

    public static void log( Exception ex ) {
        String msg = ex.getMessage() == null ? "[No details.]" : ex.getMessage(); //$NON-NLS-1$
        log( msg, ex );
    }

    public static void log( String msg, Exception ex ) {
        if( getDefault() != null ) {
            IStatus status = new Status( ERROR, getPluginId(), 0, msg, ex );
            getDefault().getLog().log( status );
        }
    }
}
