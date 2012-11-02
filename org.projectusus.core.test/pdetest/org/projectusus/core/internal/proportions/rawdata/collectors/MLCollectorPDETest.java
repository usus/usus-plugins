package org.projectusus.core.internal.proportions.rawdata.collectors;

import static org.junit.Assert.assertEquals;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.junit.Test;
import org.projectusus.core.internal.proportions.rawdata.PDETestForMetricsComputation;
import org.projectusus.statistics.MethodLengthStatistic;

public class MLCollectorPDETest extends PDETestForMetricsComputation {

    @Test
    public void emptyMethod() throws Exception {
        createFileAndBuild( "_empty" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, getNumberOfMethods() );
        assertEquals( 0, getMethodLengths() );
    }

    private int getMethodLengths() {
        MethodLengthStatistic r = new MethodLengthStatistic();
        r.visit();
        return r.getMetricsSum();
    }

    @Test
    public void oneAssignment() throws Exception {
        createFileAndBuild( "_assign" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, getNumberOfMethods() );
        assertEquals( 1, getMethodLengths() );
    }

    @Test
    public void oneIfThenElse() throws Exception {
        createFileAndBuild( "_ite" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, getNumberOfMethods() );
        assertEquals( 1, getMethodLengths() );
    }

    @Test
    public void oneIfThenElseWithAssignments() throws Exception {
        createFileAndBuild( "_iteassign" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, getNumberOfMethods() );
        assertEquals( 3, getMethodLengths() );
    }

    @Test
    public void oneInitializer() throws Exception {
        createFileAndBuild( "_init" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, getNumberOfMethods() );
        assertEquals( 1, getMethodLengths() );
    }

    @Test
    public void oneStaticInitializer() throws Exception {
        createFileAndBuild( "_staticinit" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, getNumberOfMethods() );
        assertEquals( 1, getMethodLengths() );
    }

    @Test
    public void oneAnonymous() throws Exception {
        createFileAndBuild( "_anon" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, getNumberOfMethods() );
        assertEquals( 1, getMethodLengths() );
    }

    @Test
    public void oneAnonymous3after() throws Exception {
        createFileAndBuild( "_anon3after" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, getNumberOfMethods() );
        assertEquals( 4, getMethodLengths() );
    }

    @Test
    public void oneAnonymous3before() throws Exception {
        createFileAndBuild( "_anon3before" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, getNumberOfMethods() );
        assertEquals( 4, getMethodLengths() );
    }

    @Test
    public void oneAnonymousWithMethod() throws Exception {
        createFileAndBuild( "_anonWmeth" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 2, getNumberOfMethods() );
        assertEquals( 1, getMethodLengths() );
    }

    @Test
    public void oneAnonymousWithMethod3after() throws Exception {
        createFileAndBuild( "_anonWmeth3after" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 2, getNumberOfMethods() );
        assertEquals( 4, getMethodLengths() );
    }

    @Test
    public void oneAnonymousWithMethod3before() throws Exception {
        createFileAndBuild( "_anonWmeth3before" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 2, getNumberOfMethods() );
        assertEquals( 4, getMethodLengths() );
    }

    @Test
    public void oneAnonymousWith4Methods() throws Exception {
        createFileAndBuild( "_anonW4meth" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 5, getNumberOfMethods() );
        assertEquals( 7, getMethodLengths() );
    }

    @Test
    public void twoClassesWithOneAssignment() throws Exception {
        createFileAndBuild( "_2assign" );
        assertEquals( 2, getNumberOfClasses() );
        assertEquals( 2, getNumberOfMethods() );
        assertEquals( 2, getMethodLengths() );
    }

    @Test
    public void interfaceBug() throws Exception {
        // org.eclipse.core.runtime.content
        project.createFolder( "org" );
        project.createFolder( "org/eclipse" );
        project.createFolder( "org/eclipse/core" );
        project.createFolder( "org/eclipse/core/runtime" );
        project.createFolder( "org/eclipse/core/runtime/content" );
        project.createFile( "org/eclipse/core/runtime/content/IContentTypeSettings.java", loadResource( "IContentTypeSettings.test" ) );
        workspace.buildFullyAndWait();
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 6, getNumberOfMethods() );
        assertEquals( 0, getMethodLengths() );
    }

    protected IFile createFileAndBuild( String filenumber ) throws CoreException, Exception {
        return super.createFileAndBuild( "ML" + filenumber );
    }
}
