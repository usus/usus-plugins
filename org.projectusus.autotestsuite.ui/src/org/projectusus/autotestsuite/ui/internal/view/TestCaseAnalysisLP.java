// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.autotestsuite.ui.internal.view;

import static org.projectusus.autotestsuite.core.internal.TestCaseKind.JUNIT_3;
import static org.projectusus.autotestsuite.core.internal.TestCaseKind.JUNIT_4;
import static org.projectusus.autotestsuite.ui.internal.util.AutoTestSuiteUIImages.getSharedImages;
import static org.projectusus.autotestsuite.ui.internal.util.ISharedAutoTestSuiteImages.OBJ_JUNIT_3;
import static org.projectusus.autotestsuite.ui.internal.util.ISharedAutoTestSuiteImages.OBJ_JUNIT_4;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.ui.JavaElementLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.projectusus.autotestsuite.core.internal.ITestCase;
import org.projectusus.autotestsuite.core.internal.ITestContainerGraphElement;

class TestCaseAnalysisLP extends LabelProvider {

    private final JavaElementLabelProvider javaElementLP = new JavaElementLabelProvider();

    @Override
    public String getText( Object element ) {
        String result = super.getText( element );
        if( element instanceof ITestContainerGraphElement ) {
            IJavaElement javaElement = ((ITestContainerGraphElement)element).getJavaElement();
            result = javaElementLP.getText( javaElement );
        }
        return result;
    }

    @Override
    public Image getImage( Object element ) {
        Image result = super.getImage( element );
        if( element instanceof ITestCase ) {
            result = getTestCaseImage( (ITestCase)element );
        } else if( element instanceof ITestContainerGraphElement ) {
            IJavaElement javaElement = ((ITestContainerGraphElement)element).getJavaElement();
            result = javaElementLP.getImage( javaElement );
        }
        return result;
    }

    private Image getTestCaseImage( ITestCase testCase ) {
        if( JUNIT_3.isKindOf( testCase ) ) {
            return getSharedImages().getImage( OBJ_JUNIT_3 );
        } else if( JUNIT_4.isKindOf( testCase ) ) {
            return getSharedImages().getImage( OBJ_JUNIT_4 );
        }
        throw new IllegalArgumentException();
    }
}
