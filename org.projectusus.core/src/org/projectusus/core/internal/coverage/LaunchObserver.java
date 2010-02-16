// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.coverage;

import java.util.List;

import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.ILaunchesListener;
import org.projectusus.core.internal.coverage.emmadriver.EmmaDriver;

public class LaunchObserver {

    private ILaunchesListener launchListener;
    private EmmaDriver emmaDriver;
    private List<? extends ITestSuiteForCoverageSelector> selectors;

    public void connect() {
        selectors = new ExtensionLoader().loadSelectors();
        emmaDriver = new EmmaDriver();
        launchListener = new LaunchListener( selectors, emmaDriver );
        getLaunchManager().addLaunchListener( launchListener );
    }

    public void dispose() {
        getLaunchManager().removeLaunchListener( launchListener );
        launchListener = null;
        emmaDriver.dispose();
        emmaDriver = null;
        selectors = null;
    }

    // private List<? extends ITestSuiteForCoverageSelector> createSelectors() {
    // return asList( new ITestSuiteForCoverageSelector() {
    // public boolean select( ILaunch launch ) {
    // return isRelevantTestSuite( launch.getLaunchConfiguration() );
    // }
    // } );
    // }

    private ILaunchManager getLaunchManager() {
        return DebugPlugin.getDefault().getLaunchManager();
    }
}
