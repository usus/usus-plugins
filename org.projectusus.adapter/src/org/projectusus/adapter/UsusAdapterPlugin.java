package org.projectusus.adapter;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;
import org.projectusus.core.internal.UsusCorePlugin;

public class UsusAdapterPlugin extends Plugin {

    private static UsusAdapterPlugin plugin;
    private AutoComputeSetting autoComputer;

    public static UsusAdapterPlugin getDefault() {
        return plugin;
    }

    public void start( BundleContext bundleContext ) throws Exception {
        super.start( bundleContext );
        plugin = this;
        autoComputer = new AutoComputeSetting();
    }

    public void stop( BundleContext bundleContext ) throws Exception {
        autoComputer.dispose();
    }

    public boolean getAutocompute() {
        return UsusCorePlugin.getDefault().getAutocompute();
    }

    public void setAutoCompute( boolean autoCompute ) {
        UsusCorePlugin.getDefault().setAutoCompute( autoCompute );
        autoComputer.setAutoCompute( autoCompute );
    }

}
