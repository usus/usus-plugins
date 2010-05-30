package org.projectusus.bugprison.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class BugPrisonUIPlugin extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "org.projectusus.bugprison.ui";
	private static BugPrisonUIPlugin plugin;
	
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	public static BugPrisonUIPlugin getDefault() {
		return plugin;
	}
}
