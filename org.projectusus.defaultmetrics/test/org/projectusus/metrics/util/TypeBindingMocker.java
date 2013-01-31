package org.projectusus.metrics.util;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.IPackageBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.projectusus.core.project2.IUSUSProject;

public class TypeBindingMocker {

    public static ITypeBinding createTypeBinding(String classname, String packagename) throws JavaModelException {
        ITypeBinding binding = mock( ITypeBinding.class );
        when( binding.getErasure() ).thenReturn( binding );
        when( binding.getName() ).thenReturn( classname );
        IPackageBinding packageBinding = createPackageBinding( packagename );
        when( binding.getPackage() ).thenReturn( packageBinding );
        IJavaElement javaElement = createJavaElement();
        when( binding.getJavaElement() ).thenReturn( javaElement );
        return binding;
    }

    public static IPackageBinding createPackageBinding( String packagename ) {
        IPackageBinding packageBinding = mock( IPackageBinding.class );
        when( packageBinding.getName() ).thenReturn( packagename );
        return packageBinding;
    }

    public static IJavaElement createJavaElement() throws JavaModelException {
        IJavaElement javaElement = mock( IJavaElement.class );
        IFile file = createFile();
        when( javaElement.getUnderlyingResource() ).thenReturn( file );
        return javaElement;
    }

    public static IFile createFile() {
        IFile file = mock( IFile.class );
        when( file.getFileExtension() ).thenReturn( "java" );

        IProject project = createProject();
        when( file.getProject() ).thenReturn( project );
        return file;
    }

    public static IProject createProject() {
        IProject project = mock( IProject.class );
        when( Boolean.valueOf( project.isAccessible() ) ).thenReturn( Boolean.TRUE );
        IUSUSProject ususProject = createUsusProject();
        when( project.getAdapter( IUSUSProject.class ) ).thenReturn( ususProject );
        return project;
    }

    public static IUSUSProject createUsusProject() {
        IUSUSProject ususProject = mock( IUSUSProject.class );
        when( Boolean.valueOf( ususProject.isUsusProject() ) ).thenReturn( Boolean.TRUE );
        return ususProject;
    }

}
