// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.autotestsuite.ui.internal.tabgroup;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.EnvironmentTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.debug.ui.sourcelookup.SourceLookupTab;
import org.eclipse.jdt.debug.ui.launchConfigurations.JavaArgumentsTab;
import org.eclipse.jdt.debug.ui.launchConfigurations.JavaClasspathTab;
import org.eclipse.jdt.debug.ui.launchConfigurations.JavaJRETab;

public class AutoTestSuiteTabGroup extends AbstractLaunchConfigurationTabGroup {

    public void createTabs( ILaunchConfigurationDialog dialog, String mode ) {
        ILaunchConfigurationTab[] tabs = new ILaunchConfigurationTab[] { new AutoTestSuiteTab(), new JavaArgumentsTab(), new JavaClasspathTab(), new JavaJRETab(),
                new SourceLookupTab(), new EnvironmentTab(), new CommonTab() };
        setTabs( tabs );
    }
}
