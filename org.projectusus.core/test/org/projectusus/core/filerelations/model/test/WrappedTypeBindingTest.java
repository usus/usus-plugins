package org.projectusus.core.filerelations.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.IPackageBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.filerelations.model.Classname;
import org.projectusus.core.filerelations.model.WrappedTypeBinding;
import org.projectusus.core.project2.IUSUSProject;

public class WrappedTypeBindingTest {
    private ITypeBinding binding = mock( ITypeBinding.class );
    private IFile resource = mock( IFile.class );
    private IJavaElement javaElement = mock( IJavaElement.class );
    private IProject project = mock( IProject.class );
    private IUSUSProject adapter = mock( IUSUSProject.class );
    private IPackageBinding packg = mock( IPackageBinding.class );
    private IJavaElement packageJavaElement = mock( IJavaElement.class );

    @Before
    public void setup() throws JavaModelException {
        when( new Boolean( binding.isPrimitive() ) ).thenReturn( Boolean.FALSE );
        when( binding.getErasure() ).thenReturn( binding );
        when( new Boolean( binding.isTypeVariable() ) ).thenReturn( Boolean.FALSE );
        when( new Boolean( binding.isCapture() ) ).thenReturn( Boolean.FALSE );
        when( new Boolean( binding.isWildcardType() ) ).thenReturn( Boolean.FALSE );
        when( resource.getProject() ).thenReturn( project );
        when( resource.getFileExtension() ).thenReturn( "java" ); //$NON-NLS-1$
        when( binding.getJavaElement() ).thenReturn( javaElement );
        when( javaElement.getUnderlyingResource() ).thenReturn( resource );
        when( new Boolean( project.isAccessible() ) ).thenReturn( Boolean.TRUE );
        when( project.getAdapter( any( Class.class ) ) ).thenReturn( adapter );
        when( new Boolean( adapter.isUsusProject() ) ).thenReturn( Boolean.TRUE );

        when( binding.getName() ).thenReturn( "BindingName" ); //$NON-NLS-1$
        when( binding.getPackage() ).thenReturn( packg );
        when( packg.getName() ).thenReturn( "PackageName" ); //$NON-NLS-1$
        when( packg.getJavaElement() ).thenReturn( packageJavaElement );
    }

    @Test
    public void nullTypeBindingYieldsNull() {
        assertFalse( new WrappedTypeBinding( null ).isValid() );
    }

    @Test
    public void primitiveTypeBindingYieldsNull() {
        when( new Boolean( binding.isPrimitive() ) ).thenReturn( Boolean.TRUE );
        assertFalse( new WrappedTypeBinding( binding ).isValid() );
    }

    @Test
    public void erasedBindingYieldsNull() {
        when( binding.getErasure() ).thenReturn( null );

        assertFalse( new WrappedTypeBinding( binding ).isValid() );
    }

    @Test
    public void erasedBindingTypeVariableYieldsNull() {
        when( new Boolean( binding.isTypeVariable() ) ).thenReturn( Boolean.TRUE );

        assertFalse( new WrappedTypeBinding( binding ).isValid() );
    }

    @Test
    public void erasedBindingCaptureYieldsNull() {
        when( new Boolean( binding.isCapture() ) ).thenReturn( Boolean.TRUE );

        assertFalse( new WrappedTypeBinding( binding ).isValid() );
    }

    @Test
    public void erasedBindingWildcardTypeYieldsNull() {
        when( new Boolean( binding.isWildcardType() ) ).thenReturn( Boolean.TRUE );

        assertFalse( new WrappedTypeBinding( binding ).isValid() );
    }

    @Test
    public void erasedBindingNullJavaElementYieldsNull() {
        when( binding.getJavaElement() ).thenReturn( null );

        assertFalse( new WrappedTypeBinding( binding ).isValid() );
    }

    @Test
    public void erasedBindingNullUnderlyingResourceYieldsNull() throws JavaModelException {
        when( javaElement.getUnderlyingResource() ).thenReturn( null );

        assertFalse( new WrappedTypeBinding( binding ).isValid() );
    }

    @Test
    public void erasedBindingNonJavaFileYieldsNull() {
        when( resource.getFileExtension() ).thenReturn( "xyz" ); //$NON-NLS-1$

        assertFalse( new WrappedTypeBinding( binding ).isValid() );
    }

    @Test
    public void erasedBindingProjectNotAccessibleYieldsNull() {
        when( new Boolean( project.isAccessible() ) ).thenReturn( Boolean.FALSE );

        assertFalse( new WrappedTypeBinding( binding ).isValid() );
    }

    @Test
    public void erasedBindingProjectAdapterWrongTypeYieldsNull() {
        Object wrongAdapter = mock( Object.class );
        when( project.getAdapter( any( Class.class ) ) ).thenReturn( wrongAdapter );

        assertFalse( new WrappedTypeBinding( binding ).isValid() );
    }

    @Test
    public void erasedBindingNotInUsusProjectYieldsNull() {
        when( new Boolean( adapter.isUsusProject() ) ).thenReturn( Boolean.FALSE );

        assertFalse( new WrappedTypeBinding( binding ).isValid() );
    }

    @Test
    public void erasedBindingInUsusProjectYieldsBoundType() {

        WrappedTypeBinding boundType = new WrappedTypeBinding( binding );
        assertTrue( boundType.isValid() );
        assertEquals( resource, boundType.getUnderlyingResource() );
        assertEquals( new Classname( "BindingName" ), boundType.getClassname() ); //$NON-NLS-1$
    }
}
