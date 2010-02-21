// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.autotestsuite.core.internal;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;

class TestCase implements ITestCase {

    private final IType type;

    TestCase( IType type ) {
        this.type = type;
    }

    public TestCaseKind getKind() {
        return TestCaseKind.JUNIT_4;
    }

    public IJavaElement getJavaElement() {
        return type;
    }

    public String getName() {
        return type.getElementName();
    }
}
