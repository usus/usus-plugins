// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.coverage;

import static com.mountainminds.eclemma.core.CoverageTools.LAUNCH_MODE;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchesListener;

class LaunchListener implements ILaunchesListener {

    private final ITestSuiteForCoverageSelector selector;
    private final IEmmaDriver emmaDriver;

    LaunchListener( List<? extends ITestSuiteForCoverageSelector> selectors, IEmmaDriver emmaDriver ) {
        this.selector = selectors.get( 0 );
        this.emmaDriver = emmaDriver;
    }

    public void launchesAdded( ILaunch[] launches ) {
        // TODO lf duplication to launchesRemoved()
        List<ILaunch> coverageLaunches = filterCoverageLaunches( launches );
        if( coverageLaunches.size() > 0 ) {
            if( selector.select( coverageLaunches.get( 0 ) ) ) {
                emmaDriver.setActive( true );
            }
        }
    }

    public void launchesRemoved( ILaunch[] launches ) {
        List<ILaunch> coverageLaunches = filterCoverageLaunches( launches );
        if( coverageLaunches.size() > 0 ) {
            // TODO lf all selectors
            if( selector.select( coverageLaunches.get( 0 ) ) ) {
                emmaDriver.setActive( false );
            }
        }
    }

    public void launchesChanged( ILaunch[] launches ) {
        // unused
    }

    private List<ILaunch> filterCoverageLaunches( ILaunch[] launches ) {
        List<ILaunch> result = new ArrayList<ILaunch>();
        if( launches != null ) {
            for( ILaunch launch : launches ) {
                if( isCoverageLaunch( launch ) ) {
                    result.add( launch );
                }
            }
        }
        return result;
    }

    private boolean isCoverageLaunch( ILaunch launch ) {
        return LAUNCH_MODE.equals( launch.getLaunchMode() );
    }
}
