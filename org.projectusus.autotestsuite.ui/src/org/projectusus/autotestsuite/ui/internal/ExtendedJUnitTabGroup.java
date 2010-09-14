package org.projectusus.autotestsuite.ui.internal;

import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.EnvironmentTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.debug.ui.sourcelookup.SourceLookupTab;
import org.eclipse.jdt.debug.ui.launchConfigurations.JavaArgumentsTab;
import org.eclipse.jdt.debug.ui.launchConfigurations.JavaClasspathTab;
import org.eclipse.jdt.debug.ui.launchConfigurations.JavaJRETab;
import org.eclipse.jdt.internal.junit.launcher.JUnitTabGroup;

@SuppressWarnings( "restriction" )
public class ExtendedJUnitTabGroup extends JUnitTabGroup {

    @Override
    public void createTabs( ILaunchConfigurationDialog dialog, String mode ) {
        setTabs( new ILaunchConfigurationTab[] { new ExtendedJUnitLaunchConfigurationTab(), new JavaArgumentsTab(), new JavaClasspathTab(), new JavaJRETab(),
                new SourceLookupTab(), new EnvironmentTab(), new CommonTab() } );
    }
}
