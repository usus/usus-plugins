// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.infopresenter.infomodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.junit.Test;

public class UsusInfoBuilderTest {

    private final IMethod method = mock( IMethod.class );
    private final IFile file = mock( IFile.class );
    private final IProject project = mock( IProject.class );

    @Test
    public void unavailableMethodYieldsNull() {
        IUsusInfo info = new UsusInfoBuilder( null ).create();
        assertNull( info );
    }

    @Test
    public void unavailableResourceYieldsMessageInfo() throws Exception {
        when( method.getUnderlyingResource() ).thenReturn( null );

        IUsusInfo info = new UsusInfoBuilder( method ).create();
        assertEquals( UnavailableUsusInfo.class, info.getClass() );
    }

    @Test
    public void unavailableFileDataYieldsMessageInfo() throws Exception {
        when( method.getUnderlyingResource() ).thenReturn( file );
        when( file.getProject() ).thenReturn( project );

        IUsusInfo info = new UsusInfoBuilder( method ).create();
        assertEquals( UnavailableUsusInfo.class, info.getClass() );
    }

    @Test
    public void unavailableClassDataYieldsMessageInfo() throws Exception {
        setUpRawDataHierarchy();

        IUsusInfo info = new UsusInfoBuilder( method ).create();
        assertEquals( UnavailableUsusInfo.class, info.getClass() );
    }

    @Test
    public void availableDataYieldsMeaningfulInfo() throws Exception {
        setUpRawDataHierarchy();

        IUsusInfo info = new UsusInfoBuilder( method ).create();
        assertEquals( UsusInfo.class, info.getClass() );
    }

    private void setUpRawDataHierarchy() throws JavaModelException {
        when( method.getUnderlyingResource() ).thenReturn( file );
        when( file.getProject() ).thenReturn( project );
    }
}
