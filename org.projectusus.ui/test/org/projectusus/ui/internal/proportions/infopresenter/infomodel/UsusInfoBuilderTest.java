// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.infopresenter.infomodel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.junit.Test;

public class UsusInfoBuilderTest {

    @Test
    public void unavailableMethodYieldsNull() {
        IUsusInfo info = UsusInfoBuilder.of( null );
        assertEquals( UnavailableUsusInfo.class, info.getClass() );
    }

    @Test
    public void availableDataYieldsMeaningfulInfo() throws JavaModelException {
        IMethod method = mock( IMethod.class );
        IType classMock = mock( IType.class );
        IFile fileMock = mock( IFile.class );
        when( Integer.valueOf( method.getElementType() ) ).thenReturn( Integer.valueOf( IJavaElement.METHOD ) );
        when( method.getDeclaringType() ).thenReturn( classMock );
        when( classMock.getUnderlyingResource() ).thenReturn( fileMock );
        IUsusInfo info = UsusInfoBuilder.of( method );
        assertEquals( UsusInfoForMethod.class, info.getClass() );
    }
}
