package org.projectusus.core.filerelations;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class UsusFileRelations extends Plugin {

	public static final String PLUGIN_ID = "org.projectusus.core.filerelations";

	private static UsusFileRelations plugin;
	
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	public static UsusFileRelations getDefault() {
		return plugin;
	}

}
