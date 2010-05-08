package org.projectusus.core.internal.proportions.rawdata;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.dom.IPackageBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.junit.Test;
import org.projectusus.core.filerelations.FileRelationMetrics;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.testutil.ReflectionUtil;

public class UsusModelTest {

    @Test
    public void addFileRelation() throws Exception {
        ITypeBinding sourceBinding = mock( ITypeBinding.class );
        ITypeBinding targetBinding = mock( ITypeBinding.class );
        IPackageBinding packageBindingMock = mock( IPackageBinding.class );
        when( sourceBinding.getName() ).thenReturn( "sourceName" ); //$NON-NLS-1$
        when( targetBinding.getName() ).thenReturn( "targetName" ); //$NON-NLS-1$
        when( sourceBinding.getPackage() ).thenReturn( packageBindingMock );
        when( targetBinding.getPackage() ).thenReturn( packageBindingMock );
        IJavaElement javaElementMock = mock( IJavaElement.class );
        IFile file = mock( IFile.class );
        when( javaElementMock.getUnderlyingResource() ).thenReturn( file );
        when( sourceBinding.getJavaElement() ).thenReturn( javaElementMock );
        when( targetBinding.getJavaElement() ).thenReturn( javaElementMock );
        UsusModel model = new UsusModel();
        FileRelationMetrics relations = mock( FileRelationMetrics.class );
        ReflectionUtil.setValue( model, relations, "fileRelations" ); //$NON-NLS-1$

        model.addClassReference( sourceBinding, targetBinding );

        verify( relations ).addFileRelation( any( ClassDescriptor.class ), any( ClassDescriptor.class ) );
    }
}
