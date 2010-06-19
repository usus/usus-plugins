package org.projectusus.core.internal.proportions.rawdata;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.projectusus.core.internal.UsusCorePlugin;

public class DropRawDataPDETest extends PDETestForMetricsComputation {

    @Test
    public void dropProjectWithFile1() throws Exception {
        computeFile1AndCheckPreconditions();
        UsusCorePlugin.getUsusModelForAdapter().dropRawData( project );
        checkProjectRawDataIsEmpty();
    }

    @Test
    public void dropProjectWithFiles2() throws Exception {
        computeFiles2AndCheckPreconditions();
        UsusCorePlugin.getUsusModelForAdapter().dropRawData( project );
        checkProjectRawDataIsEmpty();
    }

    private void checkProjectRawDataIsEmpty() {
        assertEquals( 0, getNumberOfClassesInProject() );
        assertEquals( 0, getNumberOfMethodsInProject() );
        assertEquals( 0, new ClassSizeStatistic().getViolations() );
        assertEquals( 0, new MethodLengthStatistic().getViolations() );
        assertEquals( 0, new CyclomaticComplexityStatistic().getViolations() );
        assertEquals( 0, getNumberOfClasses() );
    }

    private void computeFile1AndCheckPreconditions() throws Exception {
        createFileAndBuild( "Reset" + "1" );

        assertEquals( 1, getNumberOfClassesInProject() );
        assertEquals( 0, new ClassSizeStatistic().getViolations() );
        assertEquals( 2, getNumberOfMethodsInProject() );
        assertEquals( 1, new MethodLengthStatistic().getViolations() );
        assertEquals( 1, new CyclomaticComplexityStatistic().getViolations() );
        assertEquals( 1, getNumberOfClasses() );
    }

    private void computeFiles2AndCheckPreconditions() throws Exception {
        createFile( "Reset" + "2" );
        createFileAndBuild( "Reset" + "1" );

        assertEquals( 2, getNumberOfClassesInProject() );
        assertEquals( 0, new ClassSizeStatistic().getViolations() );
        assertEquals( 3, getNumberOfMethodsInProject() );
        assertEquals( 1, new MethodLengthStatistic().getViolations() );
        assertEquals( 1, new CyclomaticComplexityStatistic().getViolations() );
        assertEquals( 2, getNumberOfClasses() );
    }

    private int getNumberOfMethodsInProject() {
        return new MethodCountVisitor( new JavaModelPath( project ) ).getMethodCount();
    }

    private int getNumberOfClassesInProject() {
        return new ClassCountVisitor( new JavaModelPath( project ) ).getClassCount();
    }
}
