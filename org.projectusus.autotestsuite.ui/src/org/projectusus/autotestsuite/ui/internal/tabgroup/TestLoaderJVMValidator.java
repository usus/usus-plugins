// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.autotestsuite.ui.internal.tabgroup;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jdt.internal.junit.launcher.TestKind;
import org.eclipse.jdt.internal.junit.launcher.TestKindRegistry;
import org.eclipse.jdt.internal.junit.util.JUnitStubUtility;
import org.eclipse.jdt.launching.AbstractVMInstall;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;
import org.projectusus.autotestsuite.ui.internal.AutoTestSuitePlugin;

class TestLoaderJVMValidator {

    // TODO lf check later: hopefully we can use JDT facilities directly here

    private final ILaunchConfiguration launchConfig;
    private final TestKind testKind;

    public TestLoaderJVMValidator( ILaunchConfiguration launchConfig, TestKind testKind ) {
        this.launchConfig = launchConfig;
        this.testKind = testKind;
    }

    void validate() {
        if( launchConfig == null ) {
            return;
        }

        if( testKind == null || TestKindRegistry.JUNIT3_TEST_KIND_ID.equals( testKind.getId() ) )
            return;
        try {
            doValidateTestLoaderJVM();
        } catch( CoreException cex ) {
            AutoTestSuitePlugin.getDefault().getLog().log( cex.getStatus() );
        }
    }

    private void doValidateTestLoaderJVM() throws CoreException {
        String path = launchConfig.getAttribute( IJavaLaunchConfigurationConstants.ATTR_JRE_CONTAINER_PATH, (String)null );
        if( path != null ) {
            IVMInstall vm = JavaRuntime.getVMInstall( Path.fromPortableString( path ) );
            if( vm instanceof AbstractVMInstall ) {
                String compliance = ((AbstractVMInstall)vm).getJavaVersion();
                if( compliance != null && !JUnitStubUtility.is50OrHigher( compliance ) ) {
                    // setErrorMessage( "JDK 1.5 required" );
                    throw new IllegalStateException( "JDK 1.5 required" );
                }
            }
        }
    }
}
