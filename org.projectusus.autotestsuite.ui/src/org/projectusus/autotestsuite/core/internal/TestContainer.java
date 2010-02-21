// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.autotestsuite.core.internal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;

class TestContainer implements ITestContainer {

    private final IJavaElement element;
    private final Set<ITestContainer> containers = new HashSet<ITestContainer>();

    TestContainer( IJavaProject jProject ) {
        element = jProject;
    }

    public IJavaElement getJavaElement() {
        return element;
    }

    public String getName() {
        return element.getElementName();
    }

    public List<ITestCase> getTestCases() {
        return TestCaseKind.JUNIT_4.findTestCases( this );
    }

    public List<ITestContainer> getTestContainers() {
        ArrayList<ITestContainer> result = new ArrayList<ITestContainer>();
        result.addAll( containers );
        return result;
    }
}
