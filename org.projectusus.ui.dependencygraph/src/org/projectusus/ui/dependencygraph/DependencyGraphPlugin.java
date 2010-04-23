package org.projectusus.ui.dependencygraph;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class DependencyGraphPlugin extends AbstractUIPlugin {

    public static final String PLUGIN_ID = "org.projectusus.ui.dependencygraph";
    private static DependencyGraphPlugin plugin;

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

    public static DependencyGraphPlugin getDefault() {
        return plugin;
    }

}
