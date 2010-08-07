// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.infopresenter.infomodel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.jdt.core.IMethod;
import org.junit.Test;

public class MethodFormatterTest {

    private final IMethod javaElement = mock( IMethod.class );

    @Test
    public void formatHeadForArrayParameter() throws Exception {
        prepareExpectations( "([Ljava.lang.String;)QString;", "QString;" ); //$NON-NLS-1$ //$NON-NLS-2$

        MethodFormatter formatter = new MethodFormatter( javaElement );
        assertEquals( "String n(java.lang.String[])", formatter.formatHeadInfo() ); //$NON-NLS-1$
    }

    @Test
    public void formatHeadForMultipleParameters() throws Exception {
        prepareExpectations( "(QString;I)V", "V" ); //$NON-NLS-1$ //$NON-NLS-2$

        MethodFormatter formatter = new MethodFormatter( javaElement );
        assertEquals( "void n(String, int)", formatter.formatHeadInfo() ); //$NON-NLS-1$
    }

    @Test
    public void formatHeadWithoutParameters() throws Exception {
        prepareExpectations( "()I", "I" ); //$NON-NLS-1$ //$NON-NLS-2$

        MethodFormatter formatter = new MethodFormatter( javaElement );
        assertEquals( "int n()", formatter.formatHeadInfo() ); //$NON-NLS-1$
    }

    private void prepareExpectations( String signature, String returnType ) throws Exception {
        when( javaElement.getElementName() ).thenReturn( "n" ); //$NON-NLS-1$
        when( javaElement.getSignature() ).thenReturn( signature );
        when( javaElement.getReturnType() ).thenReturn( returnType );
    }
}
