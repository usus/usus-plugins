// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.autotestsuite.ui.internal;

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

    public static AutoTestSuitePlugin getDefault() {
        return plugin;
    }

    public static String getPluginId() {
        return getDefault().getBundle().getSymbolicName();
    }
}
