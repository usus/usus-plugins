// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.infopresenter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.jdt.core.IMethod;
import org.junit.Test;

public class MethodFormatterTest {

    private IMethod javaElement = mock( IMethod.class );

    @Test
    public void formatHeadForArrayParameter() throws Exception {
        prepareExpectations( "([Ljava.lang.String;)QString;", "QString;" );

        MethodFormatter formatter = new MethodFormatter( javaElement );
        assertThat( formatter.formatHeadInfo(), is( "String n(java.lang.String[])" ) );
    }

    @Test
    public void formatHeadForMultipleParameters() throws Exception {
        prepareExpectations( "(QString;I)V", "V" );

        MethodFormatter formatter = new MethodFormatter( javaElement );
        assertThat( formatter.formatHeadInfo(), is( "void n(String, int)" ) );
    }

    @Test
    public void formatHeadWithoutParameters() throws Exception {
        prepareExpectations( "()I", "I" );

        MethodFormatter formatter = new MethodFormatter( javaElement );
        assertThat( formatter.formatHeadInfo(), is( "int n()" ) );
    }

    private void prepareExpectations( String signature, String returnType ) throws Exception {
        when( javaElement.getElementName() ).thenReturn( "n" );
        when( javaElement.getSignature() ).thenReturn( signature );
        when( javaElement.getReturnType() ).thenReturn( returnType );
    }
}
