package org.projectusus.bugprison.core;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class BugPrisonCorePlugin extends Plugin {

	public static final String PLUGIN_ID = "org.projectusus.bugprison.core"; //$NON-NLS-1$
	private static BugPrisonCorePlugin plugin;
	
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	public static BugPrisonCorePlugin getDefault() {
		return plugin;
	}
}
