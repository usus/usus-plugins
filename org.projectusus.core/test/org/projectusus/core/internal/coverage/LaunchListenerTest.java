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
import org.junit.Test;

import com.mountainminds.eclemma.core.CoverageTools;

public class LaunchListenerTest {

    @Test
    public void coverageLaunchTriggersSelectorCallback() throws Exception {
        ILaunch launch = setupLaunchWithTarget( CoverageTools.LAUNCH_MODE );
        TestSuiteForCoverageSelector selector = new TestSuiteForCoverageSelector();
        LaunchListener listener = new LaunchListener( asList( selector ), new TestEmmaDriver() );
        listener.launchesAdded( new ILaunch[] { launch } );
        assertTrue( selector.isCalled() );
    }

    @Test
    public void debugLaunchTriggersSelectorCallback() throws Exception {
        ILaunch launch = setupLaunchWithTarget( ILaunchManager.DEBUG_MODE );
        TestSuiteForCoverageSelector selector = new TestSuiteForCoverageSelector();
        LaunchListener listener = new LaunchListener( asList( selector ),new TestEmmaDriver() );
        listener.launchesAdded( new ILaunch[] { launch } );
        assertFalse( selector.isCalled() );
    }

    private ILaunch setupLaunchWithTarget( String name ) throws Exception {
        ILaunch launch = mock( ILaunch.class );
        when( launch.getLaunchMode() ).thenReturn( name );
        return launch;
    }

    private class TestSuiteForCoverageSelector implements ITestSuiteForCoverageSelector {

        boolean called = false;

        public boolean select( ILaunch launch ) {
            called = true;
            return false;
        }

        boolean isCalled() {
            return called;
        }
    }
    
    private class TestEmmaDriver implements IEmmaDriver {
        public void setActive( boolean active ) {
            // unused
        }
    }
}
