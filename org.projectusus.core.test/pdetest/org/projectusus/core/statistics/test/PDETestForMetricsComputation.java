// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.statistics.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Platform;
import org.junit.Rule;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.internal.JavaProject;
import org.projectusus.core.internal.Workspace;
import org.projectusus.core.statistics.visitors.ClassCountVisitor;
import org.projectusus.core.statistics.visitors.MethodCountVisitor;
import org.projectusus.metrics.util.MethodLengthVisitor;

public class PDETestForMetricsComputation {

    @Rule
    public Workspace workspace = new Workspace();

    @Rule
    public JavaProject project = new JavaProject();

    public void simpleCaseTestDemo() throws Exception {
        IFile file = project.createFile( "A.java", loadResource( "A.test" ) );
        workspace.buildFullyAndWait();
        MethodLengthVisitor stat = new MethodLengthVisitor();
        stat.visit();
        assertEquals( 1, stat.getML() );
        assertEquals( 2, new MethodCountVisitor( new JavaModelPath( file.getProject() ) ).visitAndReturn().getMethodCount() );
    }

    protected IFile createJavaFile( String fileName ) throws Exception {
        return project.createFile( fileName, loadJavaResource( fileName ) );
    }

    protected String loadResource( String fileName ) throws Exception {
        return loadContent( "resources/" + fileName );
    }

    protected String loadJavaResource( String fileName ) throws Exception {
        return loadContent( "javaresources/" + fileName );
    }

    private String loadContent( String path ) throws IOException {
        return readPreservingBinaryIdentity( toURL( path ).openStream() );
    }

    private URL toURL( String path ) {
        return Platform.getBundle( "org.projectusus.core.test" ).getEntry( path );
    }

    private String readPreservingBinaryIdentity( InputStream is ) throws IOException {
        StringBuffer result = new StringBuffer();
        Reader reader = new InputStreamReader( is );
        char[] buf = new char[512];
        int charsRead = 0;
        do {
            result.append( String.valueOf( buf, 0, charsRead ) );
            charsRead = reader.read( buf );
        } while( charsRead > -1 );
        return result.toString();
    }

    protected IFile createFileAndBuild( String name ) throws Exception {
        IFile file = createFile( name );
        workspace.buildFullyAndWait();
        return file;
    }

    protected IFile createJavaFileAndBuild( String fullName ) throws Exception {
        IFile file = createJavaFile( fullName + ".java" );
        workspace.buildFullyAndWait();
        return file;
    }

    protected IFile createFile( String name ) throws Exception {
        return project.createFile( name + ".java", loadResource( name + ".test" ) );
    }

    public int getNumberOfMethods() {
        return new MethodCountVisitor().visitAndReturn().getMethodCount();
    }

    public int getNumberOfClasses() {
        return new ClassCountVisitor().visitAndReturn().getClassCount();
    }
}
