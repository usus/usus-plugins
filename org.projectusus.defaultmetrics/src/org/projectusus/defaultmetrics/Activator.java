package org.projectusus.defaultmetrics;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class Activator extends Plugin {

    public static final String PLUGIN_ID = "org.projectusus.defaultmetrics"; //$NON-NLS-1$

    private static Activator plugin;

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

    public static Activator getDefault() {
        return plugin;
    }
}
