// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.sqi;

import static org.junit.Assert.*;
import static org.projectusus.core.internal.proportions.sqi.CodeProportionKind.ML;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Platform;
import org.junit.Test;
import org.projectusus.core.internal.PDETestUsingWSProject;
import org.projectusus.core.internal.proportions.sqi.jdtdriver.FileDriver;

public class PDETestForMetricsComputation extends PDETestUsingWSProject{

    @Test
    public void simpleCase() throws Exception {
        IFile file = createWSFile( "A.java", loadContent("A.test") );
        new FileDriver( file ).compute();
        ProjectRawData results = WorkspaceRawData.getInstance().getProjectRawData( file.getProject() );
        assertEquals( 1, results.getViolationCount( ML ) );
        assertEquals( 2, results.getViolationBasis( ML ) );
    }
    
    private String loadContent(String fileName) throws Exception {
        URL entry = loadEntry( fileName );
        return readPreservingBinaryIdentity( entry.openStream());
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

}
