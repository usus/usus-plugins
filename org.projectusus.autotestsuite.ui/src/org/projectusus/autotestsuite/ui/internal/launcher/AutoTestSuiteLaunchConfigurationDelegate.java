// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.autotestsuite.ui.internal.launcher;

import static java.util.Arrays.asList;
import static org.eclipse.core.runtime.IStatus.ERROR;
import static org.projectusus.autotestsuite.ui.internal.AutoTestSuitePlugin.getPluginId;

import java.util.HashSet;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.internal.junit.launcher.ITestKind;
import org.eclipse.jdt.internal.junit.launcher.JUnitLaunchConfigurationConstants;
import org.eclipse.jdt.internal.junit.launcher.TestKindRegistry;
import org.eclipse.jdt.junit.launcher.JUnitLaunchConfigurationDelegate;

public class AutoTestSuiteLaunchConfigurationDelegate extends JUnitLaunchConfigurationDelegate {

    // TODO lf to solve: the classpath is built from config, no way to manipulate it later on
    // Also, the assumption that a single project is the execution context must hold (else
    // there might be an inconsistent class path on the launch).

    @Override
    protected IMember[] evaluateTests( ILaunchConfiguration configuration, IProgressMonitor monitor ) throws CoreException {
        HashSet<IMember> result = new HashSet<IMember>();
        ITestKind testKind = findTestKind( configuration );
        // TODO lf this will have to be a list eventually (also: use submon then)
        for( IJavaProject javaProject : asList( getJavaProject( configuration ) ) ) {
            testKind.getFinder().findTestsInContainer( javaProject, result, monitor );
        }
        checkResult( result );
        return result.toArray( new IMember[result.size()] );
    }

    private ITestKind findTestKind( ILaunchConfiguration configuration ) {
        ITestKind testKind = JUnitLaunchConfigurationConstants.getTestRunnerKind( configuration );
        if( testKind.isNull() ) {
            testKind = TestKindRegistry.getDefault().getKind( TestKindRegistry.JUNIT3_TEST_KIND_ID ); // backward compatible for launch configurations with no runner
        }
        return testKind;
    }

    private void checkResult( HashSet<IMember> result ) throws CoreException {
        if( result.isEmpty() ) {
            // TODO lf we certainly need to show _something_ to the user
            String msg = "Could not find any tests to run. (Possible causes: no projects selected in the 'Projects Covered by Usus' view; wrong test kind (JUnit3/4) selected.";
            throw new CoreException( new Status( ERROR, getPluginId(), msg ) );
        }
    }
}
