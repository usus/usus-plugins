package org.projectusus.core.internal;

import static org.junit.Assert.fail;
import static org.projectusus.core.internal.TestProjectCreator.SOURCE_FOLDER;
import static org.projectusus.core.internal.TestProjectCreator.makeUsusProject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.rules.ExternalResource;

public class JavaProject extends ExternalResource {

    private final String projectName;

    private IProject project;

    public JavaProject() {
        this( "project" );
    }

    public JavaProject( String prefix ) {
        this.projectName = prefix + "-" + System.currentTimeMillis();
    }

    public IProject get() {
        return project;
    }

    public <T> T as( Class<T> targetClass ) {
        return targetClass.cast( project.getAdapter( targetClass ) );
    }

    public IFile createFile( String fileName, String content ) throws CoreException {
        IFile result = project.getFile( SOURCE_FOLDER + "/" + fileName );
        result.create( createInputStream( content ), true, new NullProgressMonitor() );
        return result;
    }

    public IFolder createFolder( String name ) throws CoreException {
        IFolder result = project.getFolder( SOURCE_FOLDER + "/" + name );
        result.create( true, true, new NullProgressMonitor() );
        return result;
    }

    public void updateContent( IFile file, String newContent ) throws CoreException {
        file.setContents( createInputStream( newContent ), true, false, new NullProgressMonitor() );
    }

    public void delete( IResource resource ) throws CoreException {
        resource.delete( true, new NullProgressMonitor() );
    }

    private InputStream createInputStream( String content ) {
        return new ByteArrayInputStream( content.getBytes() );
    }

    public void create() throws CoreException {
        project = new TestProjectCreator( projectName ).getProject();
    }

    public void open() throws CoreException {
        project.open( new NullProgressMonitor() );
    }

    public void close() throws CoreException {
        project.close( new NullProgressMonitor() );
    }

    public void delete() throws CoreException {
        project.delete( true, null );
    }

    public void disableUsus() throws CoreException {
        makeUsusProject( false, project );
    }

    public void enableUsus() throws CoreException {
        makeUsusProject( true, project );
    }

    protected void before() throws Throwable {
        createAndReport();
    }

    @Override
    protected void after() {
        if( project != null && project.exists() ) {
            deleteAndReport();
            project = null;
        }
    }

    private void createAndReport() {
        print( "  Creating project '" + projectName + "' ..." );
        project = null;
        Exception exception = null;
        try {
            create();
        } catch( Exception e ) {
            exception = e;
        }
        logResult( exception );
    }

    public void deleteAndReport() {
        print( "  Deleting project '" + project.getName() + "' at " + System.currentTimeMillis() + " ..." );
        Exception exception = null;
        try {
            delete();
        } catch( Exception e ) {
            exception = e;
        }
        if( project.exists() ) {
            reportFailure( "Could not delete project '" + project.getName() + "'", exception );
        } else {
            println( " OK." );
        }
    }

    private void logResult( Exception exception ) {
        if( project != null && project.exists() ) {
            println( " OK." );
        } else {
            reportFailure( "Could not create project '" + projectName + "'", exception );
        }
    }

    private void reportFailure( String message, Exception exception ) {
        println( " FAILED." );
        if( exception != null ) {
            exception.printStackTrace( System.out );
            message += ": " + exception.getMessage();
        }
        fail( message );
    }

    private void print( String message ) {
        System.out.print( message );
        System.out.flush();
    }

    private void println( String message ) {
        System.out.println( message );
    }
}
