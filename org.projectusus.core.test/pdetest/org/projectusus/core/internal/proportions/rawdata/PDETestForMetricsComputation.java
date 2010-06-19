// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Platform;
import org.projectusus.core.IMetricsAccessor;
import org.projectusus.core.IUsusModel;
import org.projectusus.core.internal.PDETestUsingWSProject;
import org.projectusus.core.internal.UsusCorePlugin;

public class PDETestForMetricsComputation extends PDETestUsingWSProject {

    public void simpleCaseTestDemo() throws Exception {
        IFile file = createWSFile( "A.java", loadContent( "A.test" ) );
        buildFullyAndWait();
        assertEquals( 1, new MethodLengthStatistic().getViolations() );
        assertEquals( 2, new MethodCountVisitor( new JavaModelPath( file.getProject() ) ).getMethodCount() );
    }

    protected String loadContent( String fileName ) throws Exception {
        URL entry = loadEntry( fileName );
        return readPreservingBinaryIdentity( entry.openStream() );
    }

    private URL loadEntry( String fileName ) {
        return Platform.getBundle( "org.projectusus.core.test" ).getEntry( "resources/" + fileName );
    }

    protected IFile createJavaWSFile( String fileName ) throws Exception {
        return createWSFile( fileName, loadJavaContent( fileName ) );
    }

    protected String loadJavaContent( String fileName ) throws Exception {
        URL entry = loadJavaEntry( fileName );
        return readPreservingBinaryIdentity( entry.openStream() );
    }

    private URL loadJavaEntry( String fileName ) {
        return Platform.getBundle( "org.projectusus.core.test" ).getEntry( "javaresources/" + fileName );
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
        buildFullyAndWait();
        return file;
    }

    protected IFile createFile( String name ) throws Exception {
        return createWSFile( name + ".java", loadContent( name + ".test" ) );
    }

    public IMetricsAccessor getMetricsAccessor() {
        return UsusCorePlugin.getMetricsAccessor();
    }

    public IUsusModel getModel() {
        return UsusCorePlugin.getUsusModel();
    }

    public int getNumberOfMethods() {
        return new MethodCountVisitor().getMethodCount();
    }

    public int getNumberOfClasses() {
        return new ClassCountVisitor().getClassCount();
    }
}
