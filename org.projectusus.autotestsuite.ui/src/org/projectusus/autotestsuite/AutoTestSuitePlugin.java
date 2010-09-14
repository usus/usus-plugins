// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.autotestsuite;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class AutoTestSuitePlugin extends AbstractUIPlugin {

    private static AutoTestSuitePlugin plugin;

    @Override
    public void start( BundleContext context ) throws Exception {
        super.start( context );
        plugin = this;
    }

    @Override
    public void stop( BundleContext context ) throws Exception {
        plugin = null;
        super.stop( context );
    }

    public static void log( Throwable throwable ) {
        log( throwable, null );
    }

    public static void logStatusOf( CoreException exception ) {
        log( exception.getStatus() );
    }

    public static void log( Throwable throwable, String message ) {
        log( new Status( IStatus.ERROR, getPluginId(), message, throwable ) );
    }

    private static void log( IStatus status ) {
        AutoTestSuitePlugin plugin = getDefault();
        if( plugin != null ) {
            plugin.getLog().log( status );
        }
    }

    public static AutoTestSuitePlugin getDefault() {
        return plugin;
    }

    public static String getPluginId() {
        return getDefault().getBundle().getSymbolicName();
    }
}
