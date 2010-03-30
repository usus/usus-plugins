// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.testdefect;

import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.junit.After;
import org.junit.Test;
import org.projectusus.core.internal.PDETestUsingWSProject;
import org.projectusus.core.internal.UsusCorePlugin;
import org.projectusus.core.internal.proportions.IUsusModel;
import org.projectusus.core.internal.proportions.IUsusModelWriteAccess;
import org.projectusus.core.internal.proportions.model.AcdSQIComputer;
import org.projectusus.core.internal.proportions.modelcomputation.ICodeProportionComputationTarget;
import org.projectusus.core.internal.proportions.rawdata.CodeProportionUnit;
import org.projectusus.core.internal.proportions.rawdata.jdtdriver.JavaFileDriver;


public class BrokenPDETest extends PDETestUsingWSProject {

    // from ProjectChangeNotificationsPDETest:
    
    
    @Test
    public void projectClosed() throws Exception {
        TestResourceChangeListener listener = new TestResourceChangeListener();
        getWorkspace().addResourceChangeListener( listener );
        project.close( new NullProgressMonitor() );
        waitForAutobuild();
        
        listener.assertNoException();
        
        ICodeProportionComputationTarget target = listener.getTarget();
        assertEquals( 0, target.getProjects().size() );
        assertEquals( 1, target.getRemovedProjects().size() );
        IProject removedProject = target.getRemovedProjects().iterator().next();
        assertEquals( project, removedProject );

        getWorkspace().removeResourceChangeListener( listener );
        super.tearDown();
    }
        
    // from ACDCollectorPDETest:
    @Test
    public void twoRelatedClasses1knows2Generic() throws Exception {
        IUsusModelWriteAccess writeModel = UsusCorePlugin.getUsusModelWriteAccess();
        writeModel.dropRawData( project );
        addJavaNature();
        IUsusModel model = UsusCorePlugin.getUsusModel();
        createAndCompute( "_1knows2generic", "Acd" );
        assertEquals( 2, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 0.75, new AcdSQIComputer().getRelativeACD(), 0.0001 );
    }
    
    
    // from PDETestForMetricsComputation:
    public IFile createAndCompute( String filenumber, String testPrefix ) throws CoreException, Exception {
        IFile file = createWSFile( testPrefix+filenumber +".java", loadContent(testPrefix+filenumber+".test") );
        computeJavaFile(file);
        return file;
    }

    protected void computeJavaFile(IFile file){
        new JavaFileDriver( file ).compute();
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


}
