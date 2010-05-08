// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.coveredprojects;

import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.projectusus.core.ITestSuiteForCoverageSelector;

public class TestSuiteSelector implements ITestSuiteForCoverageSelector {

    private String relevantLaunchConfig = null;

    public boolean select( ILaunch launch ) {
        return isRelevantTestSuite( launch.getLaunchConfiguration() );
    }

    private boolean isRelevantTestSuite( ILaunchConfiguration configuration ) {
        boolean result = false;
        if( configuration != null ) {
            result = isRelevantTestSuite( configuration, configuration.getName() );
        }
        return result;
    }

    private boolean isRelevantTestSuite( ILaunchConfiguration configuration, String configName ) {
        boolean result = false;
        if( relevantLaunchConfig != null ) {
            result = relevantLaunchConfig.equals( configName );
        } else if( userConfirms( configuration ) ) {
            relevantLaunchConfig = configName;
            result = true;
        }
        return result;
    }

    private boolean userConfirms( ILaunchConfiguration configuration ) {
        return new TestSuiteSelectorDialogOpener().openDialog();
    }
}
