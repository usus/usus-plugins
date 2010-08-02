package org.projectusus.adapter;

import static org.projectusus.core.UsusPreferenceKeys.AUTO_COMPUTE;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.osgi.framework.BundleContext;
import org.projectusus.core.UsusCorePlugin;

public class UsusAdapterPlugin extends Plugin {

    public static final String PLUGIN_ID = "org.projectusus.adapter";

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
        return getUsusPreferences().getBoolean( AUTO_COMPUTE, true );
    }

    public void setAutoCompute( boolean autoCompute ) {
        getUsusPreferences().putBoolean( AUTO_COMPUTE, autoCompute );
        autoComputer.setAutoCompute( autoCompute );
        UsusCorePlugin.getDefault().savePreferences();
    }

    private IEclipsePreferences getUsusPreferences() {
        return UsusCorePlugin.getDefault().getPreferences();
    }

}
