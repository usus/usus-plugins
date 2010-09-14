// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.autotestsuite.ui.internal.util;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.projectusus.autotestsuite.AutoTestSuitePlugin;

public interface ISharedAutoTestSuiteImages {

    // prefix all constants with the plugin id
    String ID = AutoTestSuitePlugin.getPluginId();

    String OBJ_JUNIT_3 = ID + ".OBJ_JUNIT_3";
    String OBJ_JUNIT_4 = ID + ".OBJ_JUNIT_4";
    String OBJ_TAB = ID + ".OBJ_TAB";

    Image getImage( String key );

    ImageDescriptor getDescriptor( String key );
}
