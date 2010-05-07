// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.coverage;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchManager;
import org.junit.Before;
import org.junit.Test;

import com.mountainminds.eclemma.core.CoverageTools;

public class LaunchListenerTest {

    EmmaDriver testDriver;

    @Before
    public void setupMocks() {
        testDriver = mock( EmmaDriver.class );
    }

    @Test
    public void coverageLaunchTriggersSelectorCallback() {
        ILaunch launch = setupLaunchWithTarget( CoverageTools.LAUNCH_MODE );
        TestSuiteForCoverageSelector selector = new TestSuiteForCoverageSelector();
        LaunchListener listener = new LaunchListener( asList( selector ), testDriver );
        listener.launchesAdded( new ILaunch[] { launch } );
        assertTrue( selector.isCalled() );
    }

    @Test
    public void debugLaunchTriggersSelectorCallback() {
        ILaunch launch = setupLaunchWithTarget( ILaunchManager.DEBUG_MODE );
        TestSuiteForCoverageSelector selector = new TestSuiteForCoverageSelector();
        LaunchListener listener = new LaunchListener( asList( selector ), testDriver );
        listener.launchesAdded( new ILaunch[] { launch } );
        assertFalse( selector.isCalled() );
    }

    private ILaunch setupLaunchWithTarget( String name ) {
        ILaunch launch = mock( ILaunch.class );
        when( launch.getLaunchMode() ).thenReturn( name );
        return launch;
    }

    private class TestSuiteForCoverageSelector implements ITestSuiteForCoverageSelector {

        boolean called = false;

        /**
         * @param launch
         *            is not used
         */
        public boolean select( ILaunch launch ) {
            called = true;
            return false;
        }

        boolean isCalled() {
            return called;
        }
    }

}
