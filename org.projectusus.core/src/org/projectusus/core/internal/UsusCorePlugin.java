// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal;

import static org.eclipse.core.runtime.IStatus.ERROR;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.framework.BundleContext;
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

    public IEclipsePreferences getPreferences() {
        return new InstanceScope().getNode( getPluginId() );
    }

    @Override
    public void start( final BundleContext context ) throws Exception {
        super.start( context );
        plugin = this;
        launchObserver.connect();
    }

    @Override
    public void stop( BundleContext context ) throws Exception {
        launchObserver.dispose();
        ((UsusModel)UsusModel.getUsusModel()).dispose();
        plugin = null;
        super.stop( context );
    }
}
