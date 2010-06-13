package org.projectusus.core.internal.proportions.rawdata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.junit.Before;
import org.junit.Test;

public class MetricsResultPathTest {

    private IProject project;
    private IFile file;
    private IType type;
    private IMethod method;

    @Before
    public void setup() throws JavaModelException {
        project = mock( IProject.class );
        file = getFileMockFor( project );
        type = getTypeMockFor( file );
        method = getMethodMockFor( type );
    }

    @Test
    public void defaultIsUnrestricted() {
        JavaModelPath result = new JavaModelPath();

        assertFalse( result.isRestrictedToProject() );
        assertFalse( result.isRestrictedToFile() );
        assertFalse( result.isRestrictedToType() );
        assertFalse( result.isRestrictedToMethod() );
    }

    @Test
    public void restrictedToProject() {
        JavaModelPath result = new JavaModelPath( project );

        assertTrue( result.isRestrictedToProject() );
        assertFalse( result.isRestrictedToFile() );
        assertFalse( result.isRestrictedToType() );
        assertFalse( result.isRestrictedToMethod() );
    }

    @Test
    public void restrictedToProjectGetProject() {
        JavaModelPath result = new JavaModelPath( project );

        assertEquals( project, result.getProject() );
    }

    @Test
    public void restrictedToFile() {
        JavaModelPath result = new JavaModelPath( file );

        assertTrue( result.isRestrictedToProject() );
        assertTrue( result.isRestrictedToFile() );
        assertFalse( result.isRestrictedToType() );
        assertFalse( result.isRestrictedToMethod() );
    }

    @Test
    public void restrictedToFileGetProjectAndFile() {
        JavaModelPath result = new JavaModelPath( file );

        assertEquals( file, result.getFile() );
        assertEquals( project, result.getProject() );
    }

    @Test
    public void restrictedToType() throws JavaModelException {
        JavaModelPath result = new JavaModelPath( type );

        assertTrue( result.isRestrictedToProject() );
        assertTrue( result.isRestrictedToFile() );
        assertTrue( result.isRestrictedToType() );
        assertFalse( result.isRestrictedToMethod() );
    }

    @Test
    public void restrictedToFileGetProjectToType() throws JavaModelException {
        JavaModelPath result = new JavaModelPath( type );

        assertEquals( type, result.getType() );
        assertEquals( file, result.getFile() );
        assertEquals( project, result.getProject() );
    }

    @Test
    public void restrictedToMethod() throws JavaModelException {
        JavaModelPath result = new JavaModelPath( method );

        assertTrue( result.isRestrictedToProject() );
        assertTrue( result.isRestrictedToFile() );
        assertTrue( result.isRestrictedToType() );
        assertTrue( result.isRestrictedToMethod() );
    }

    @Test
    public void restrictedToMethodGetProjectToMethod() throws JavaModelException {
        JavaModelPath result = new JavaModelPath( method );

        assertEquals( method, result.getMethod() );
        assertEquals( type, result.getType() );
        assertEquals( file, result.getFile() );
        assertEquals( project, result.getProject() );
    }

    private IMethod getMethodMockFor( IType typeToReturn ) {
        IMethod methodMock = mock( IMethod.class );
        when( methodMock.getAncestor( IJavaElement.TYPE ) ).thenReturn( typeToReturn );
        return methodMock;
    }

    private IFile getFileMockFor( IProject projectToReturn ) {
        IFile fileMock = mock( IFile.class );
        when( fileMock.getProject() ).thenReturn( projectToReturn );
        return fileMock;
    }

    private IType getTypeMockFor( IFile fileToReturn ) throws JavaModelException {
        IType typeMock = mock( IType.class );
        when( typeMock.getUnderlyingResource() ).thenReturn( fileToReturn );
        return typeMock;
    }
}
