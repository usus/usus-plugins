// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import static org.junit.Assert.assertEquals;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.ML;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.projectusus.core.internal.PDETestUsingWSProject;
import org.projectusus.core.internal.proportions.rawdata.CodeProportionUnit;
import org.projectusus.core.internal.proportions.rawdata.IProjectRawData;
import org.projectusus.core.internal.proportions.rawdata.jdtdriver.FileDriver;

public class PDETestForMetricsComputation extends PDETestUsingWSProject {

    public void simpleCaseTestDemo() throws Exception {
        IFile file = createWSFile( "A.java", loadContent( "A.test" ) );
        computeFile( file );
        IProjectRawData results = (IProjectRawData)file.getProject().getAdapter( IProjectRawData.class );
        assertEquals( 1, results.getViolationCount( ML ) );
        assertEquals( 2, results.getNumberOf( CodeProportionUnit.METHOD ) );
    }
    
    protected void computeFile(IFile file){
       new FileDriver( file ).compute();
    }

    protected String loadContent( String fileName ) throws Exception {
        URL entry = loadEntry( fileName );
        return readPreservingBinaryIdentity( entry.openStream() );
    }

    private URL loadEntry( String fileName ) {
        return Platform.getBundle( "org.projectusus.core.test" ).getEntry( "resources/" + fileName );
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

    public IFile createAndCompute( String filenumber, String testPrefix ) throws CoreException, Exception {
        IFile file = createWSFile( testPrefix+filenumber +".java", loadContent(testPrefix+filenumber+".test") );
        computeFile(file);
        return file;
    }
}
