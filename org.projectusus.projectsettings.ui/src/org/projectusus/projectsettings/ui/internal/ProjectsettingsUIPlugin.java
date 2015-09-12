// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.projectsettings.ui.internal;

import static org.eclipse.core.runtime.IStatus.ERROR;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class ProjectsettingsUIPlugin extends AbstractUIPlugin {

    private static ProjectsettingsUIPlugin plugin;

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

    public static ProjectsettingsUIPlugin getDefault() {
        return plugin;
    }

    public static String getPluginId() {
        return getDefault().getBundle().getSymbolicName();
    }

    public void log( Exception ex ) {
        String msg = ex.getMessage() == null ? "[No details.]" : ex.getMessage(); //$NON-NLS-1$
        IStatus status = new Status( ERROR, getPluginId(), 0, msg, ex );
        getDefault().getLog().log( status );
    }

}
