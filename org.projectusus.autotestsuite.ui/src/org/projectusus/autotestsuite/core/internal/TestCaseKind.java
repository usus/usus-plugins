// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.autotestsuite.core.internal;

import static org.projectusus.autotestsuite.core.internal.util.TracingOption.TCA;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.internal.junit.launcher.ITestFinder;
import org.eclipse.jdt.internal.junit.launcher.ITestKind;
import org.eclipse.jdt.internal.junit.launcher.TestKindRegistry;

// this makes heavy use of JDT internals, but I haven't found official API
@SuppressWarnings( "restriction" )
public enum TestCaseKind {

    JUNIT_4( TestKindRegistry.JUNIT4_TEST_KIND_ID ), //
    JUNIT_3( TestKindRegistry.JUNIT4_TEST_KIND_ID );

    private final String jdtId;

    private TestCaseKind( String jdtId ) {
        this.jdtId = jdtId;
    }

    public boolean isKindOf( ITestCase testCase ) {
        boolean result = false;
        try {
            IType javaElement = (IType)testCase.getJavaElement();
            result = loadTestFinder().isTest( javaElement );
        } catch( CoreException cex ) {
            TCA.trace( cex );
        }
        return result;
    }

    List<ITestCase> findTestCases( ITestContainer container ) {
        List<ITestCase> result = new ArrayList<ITestCase>();
        try {
            for( IType testCase : runTestFinder( container, loadTestFinder() ) ) {
                result.add( new TestCase( testCase ) );
            }
        } catch( CoreException cex ) {
            TCA.trace( cex );
        }
        return result;
    }

    private ITestFinder loadTestFinder() {
        ITestKind jdtTestKind = TestKindRegistry.getDefault().getKind( jdtId );
        return jdtTestKind.getFinder();
    }

    private Set<IType> runTestFinder( ITestContainer container, ITestFinder testFinder ) throws CoreException {
        Set<IType> collector = new HashSet<IType>();
        IProgressMonitor mon = new NullProgressMonitor();
        testFinder.findTestsInContainer( container.getJavaElement(), collector, mon );
        return collector;
    }
}
