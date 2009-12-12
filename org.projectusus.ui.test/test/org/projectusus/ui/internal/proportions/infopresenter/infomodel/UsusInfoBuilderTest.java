// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.infopresenter.infomodel;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.junit.Test;
import org.projectusus.core.internal.proportions.rawdata.IClassRawData;
import org.projectusus.core.internal.proportions.rawdata.IFileRawData;
import org.projectusus.core.internal.proportions.rawdata.IMethodRawData;
import org.projectusus.core.internal.proportions.rawdata.IProjectRawData;

public class UsusInfoBuilderTest {

    private IMethod method = mock( IMethod.class );
    private IFile file = mock( IFile.class );
    private IProject project = mock( IProject.class );
    private IProjectRawData projectRawData = mock( IProjectRawData.class );
    private IFileRawData fileRawData = mock( IFileRawData.class );

    @Test
    public void unavailableMethodYieldsNull() throws Exception {
        IUsusInfo info = new UsusInfoBuilder( null ).create();
        assertThat( info, is( nullValue() ) );
    }

    @Test
    public void unavailableResourceYieldsMessageInfo() throws Exception {
        when( method.getUnderlyingResource() ).thenReturn( null );

        IUsusInfo info = new UsusInfoBuilder( method ).create();
        assertThat( info, is( UnavailableUsusInfo.class ) );
    }

    @Test
    public void unavailableFileDataYieldsMessageInfo() throws Exception {
        when( method.getUnderlyingResource() ).thenReturn( file );
        when( file.getProject() ).thenReturn( project );

        IUsusInfo info = new UsusInfoBuilder( method ).create();
        assertThat( info, is( UnavailableUsusInfo.class ) );
    }

    @Test
    public void unavailableClassDataYieldsMessageInfo() throws Exception {
        setUpRawDataHierarchy();

        IUsusInfo info = new UsusInfoBuilder( method ).create();
        assertThat( info, is( UnavailableUsusInfo.class ) );
    }

    @Test
    public void availableDataYieldsMeaningfulInfo() throws Exception {
        setUpRawDataHierarchy();
        setupJavaElementRawData();

        IUsusInfo info = new UsusInfoBuilder( method ).create();
        assertThat( info, is( UsusInfo.class ) );
    }

    private void setupJavaElementRawData() {
        IClassRawData classRawData = mock( IClassRawData.class );
        when( fileRawData.getRawData( method.getDeclaringType() ) ).thenReturn( classRawData );
        IMethodRawData methodRawData = mock( IMethodRawData.class );
        when( classRawData.getRawData( method ) ).thenReturn( methodRawData );
    }

    private void setUpRawDataHierarchy() throws JavaModelException {
        when( method.getUnderlyingResource() ).thenReturn( file );
        when( file.getProject() ).thenReturn( project );
        when( project.getAdapter( IProjectRawData.class ) ).thenReturn( projectRawData );
        when( projectRawData.getFileRawData( file ) ).thenReturn( fileRawData );
    }
}
