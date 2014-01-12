package org.projectusus.core.filerelations.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
import org.projectusus.core.filerelations.model.TypeBindingChecker;
import org.projectusus.core.filerelations.model.WrappedTypeBinding;
import org.projectusus.core.project2.IUSUSProject;

public class TypeBindingCheckerTest {

    private ITypeBinding binding = mock( ITypeBinding.class );
    private IFile resource = mock( IFile.class );
    private IJavaElement javaElement = mock( IJavaElement.class );
    private IProject project = mock( IProject.class );
    private IUSUSProject adapter = mock( IUSUSProject.class );
    private IPackageBinding packg = mock( IPackageBinding.class );
    private IJavaElement packageJavaElement = mock( IJavaElement.class );

    private TypeBindingChecker checker = new TypeBindingChecker();

    @Before
    public void setup() throws JavaModelException {
        when( new Boolean( binding.isPrimitive() ) ).thenReturn( Boolean.FALSE );
        when( binding.getErasure() ).thenReturn( binding );
        when( new Boolean( binding.isTypeVariable() ) ).thenReturn( Boolean.FALSE );
        when( new Boolean( binding.isCapture() ) ).thenReturn( Boolean.FALSE );
        when( new Boolean( binding.isWildcardType() ) ).thenReturn( Boolean.FALSE );
        when( new Boolean( binding.isAnonymous() ) ).thenReturn( Boolean.FALSE );
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
    public void nullTypeBindingYieldsInvalidWrapper() {
        assertNull( checker.checkForRelevanceAndWrap( null ) );
    }

    @Test
    public void primitiveTypeBindingYieldsInvalidWrapper() {
        when( new Boolean( binding.isPrimitive() ) ).thenReturn( Boolean.TRUE );
        assertNull( checker.checkForRelevanceAndWrap( binding ) );
    }

    @Test
    public void erasedBindingYieldsInvalidWrapper() {
        when( binding.getErasure() ).thenReturn( null );

        assertNull( checker.checkForRelevanceAndWrap( binding ) );
    }

    @Test
    public void erasedBindingTypeVariableYieldsInvalidWrapper() {
        when( new Boolean( binding.isTypeVariable() ) ).thenReturn( Boolean.TRUE );

        assertNull( checker.checkForRelevanceAndWrap( binding ) );
    }

    @Test
    public void erasedBindingCaptureYieldsInvalidWrapper() {
        when( new Boolean( binding.isCapture() ) ).thenReturn( Boolean.TRUE );

        assertNull( checker.checkForRelevanceAndWrap( binding ) );
    }

    @Test
    public void erasedBindingWildcardTypeYieldsInvalidWrapper() {
        when( new Boolean( binding.isWildcardType() ) ).thenReturn( Boolean.TRUE );

        assertNull( checker.checkForRelevanceAndWrap( binding ) );
    }

    @Test
    public void erasedBindingNullJavaElementYieldsInvalidWrapper() {
        when( binding.getJavaElement() ).thenReturn( null );

        assertNull( checker.checkForRelevanceAndWrap( binding ) );
    }

    @Test
    public void erasedBindingNullUnderlyingResourceYieldsInvalidWrapper() throws JavaModelException {
        when( javaElement.getUnderlyingResource() ).thenReturn( null );

        assertNull( checker.checkForRelevanceAndWrap( binding ) );
    }

    @Test
    public void erasedBindingNonJavaFileYieldsInvalidWrapper() {
        when( resource.getFileExtension() ).thenReturn( "xyz" ); //$NON-NLS-1$

        assertNull( checker.checkForRelevanceAndWrap( binding ) );
    }

    @Test
    public void erasedBindingProjectNotAccessibleYieldsInvalidWrapper() {
        when( new Boolean( project.isAccessible() ) ).thenReturn( Boolean.FALSE );

        assertNull( checker.checkForRelevanceAndWrap( binding ) );
    }

    @Test
    public void erasedBindingProjectAdapterWrongTypeYieldsInvalidWrapper() {
        Object wrongAdapter = mock( Object.class );
        when( project.getAdapter( any( Class.class ) ) ).thenReturn( wrongAdapter );

        assertNull( checker.checkForRelevanceAndWrap( binding ) );
    }

    @Test
    public void erasedBindingNotInUsusProjectYieldsInvalidWrapper() {
        when( new Boolean( adapter.isUsusProject() ) ).thenReturn( Boolean.FALSE );

        assertNull( checker.checkForRelevanceAndWrap( binding ) );
    }

    @Test
    public void erasedBindingInUsusProjectYieldsBoundType() {

        WrappedTypeBinding boundType = checker.checkForRelevanceAndWrap( binding );
        assertNotNull( boundType );
        assertEquals( resource, boundType.getUnderlyingResource() );
        assertEquals( new Classname( "BindingName" ), boundType.getClassname() ); //$NON-NLS-1$
    }
}
