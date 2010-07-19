// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core;

import static org.eclipse.core.runtime.IStatus.ERROR;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.service.prefs.BackingStoreException;

public class UsusCorePlugin extends Plugin {

    // using hard-coded string instead of bundle symbolic name, since we
    // cannot rely on the bundle still being there during shutdown when
    // some jobs still run
    public static final String PLUGIN_ID = "org.projectusus.core"; //$NON-NLS-1$
    private static UsusCorePlugin plugin;

    public static UsusCorePlugin getDefault() {
        return plugin;
    }

    public static void log( Throwable throwable ) {
        String msg = throwable.getMessage() == null ? "[No details.]" : throwable.getMessage(); //$NON-NLS-1$
        log( msg, throwable );
    }

    public static void log( String msg, Throwable throwable ) {
        if( getDefault() != null ) {
            IStatus status = new Status( ERROR, PLUGIN_ID, 0, msg, throwable );
            getDefault().getLog().log( status );
        }
    }

    public IEclipsePreferences getPreferences() {
        return new InstanceScope().getNode( PLUGIN_ID );
    }

    public void savePreferences() {
        try {
            getPreferences().flush();
        } catch( BackingStoreException bastox ) {
            UsusCorePlugin.log( bastox );
        }
    }

    @Override
    public void start( final BundleContext context ) throws Exception {
        super.start( context );
        plugin = this;
        context.addBundleListener( new BundleListener() {
            public void bundleChanged( BundleEvent event ) {
                if( event.getType() == BundleEvent.STARTED ) {
                    context.removeBundleListener( this );
                }
            }
        } );
    }

    @Override
    public void stop( BundleContext context ) throws Exception {
        plugin.savePreferences();
        plugin = null;
        super.stop( context );
    }

}
