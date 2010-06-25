package org.projectusus.core.internal.proportions.rawdata;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.statistics.ClassCountVisitor;
import org.projectusus.core.statistics.ClassSizeStatistic;
import org.projectusus.core.statistics.CyclomaticComplexityStatistic;
import org.projectusus.core.statistics.MethodCountVisitor;
import org.projectusus.core.statistics.MethodLengthStatistic;

public class DropRawDataPDETest extends PDETestForMetricsComputation {

    @Test
    public void dropProjectWithFile1() throws Exception {
        computeFile1AndCheckPreconditions();
        UsusModel.ususModel().dropRawData( project );
        checkProjectRawDataIsEmpty();
    }

    @Test
    public void dropProjectWithFiles2() throws Exception {
        computeFiles2AndCheckPreconditions();
        UsusModel.ususModel().dropRawData( project );
        checkProjectRawDataIsEmpty();
    }

    private void checkProjectRawDataIsEmpty() {
        assertEquals( 0, getNumberOfClassesInProject() );
        assertEquals( 0, getNumberOfMethodsInProject() );
        assertEquals( 0, new ClassSizeStatistic().visit().getViolations() );
        assertEquals( 0, new MethodLengthStatistic().visit().getViolations() );
        assertEquals( 0, new CyclomaticComplexityStatistic().visit().getViolations() );
        assertEquals( 0, getNumberOfClasses() );
    }

    private void computeFile1AndCheckPreconditions() throws Exception {
        createFileAndBuild( "Reset" + "1" );

        assertEquals( 1, getNumberOfClassesInProject() );
        assertEquals( 0, new ClassSizeStatistic().visit().getViolations() );
        assertEquals( 2, getNumberOfMethodsInProject() );
        assertEquals( 1, new MethodLengthStatistic().visit().getViolations() );
        assertEquals( 1, new CyclomaticComplexityStatistic().visit().getViolations() );
        assertEquals( 1, getNumberOfClasses() );
    }

    private void computeFiles2AndCheckPreconditions() throws Exception {
        createFile( "Reset" + "2" );
        createFileAndBuild( "Reset" + "1" );

        assertEquals( 2, getNumberOfClassesInProject() );
        assertEquals( 0, new ClassSizeStatistic().visit().getViolations() );
        assertEquals( 3, getNumberOfMethodsInProject() );
        assertEquals( 1, new MethodLengthStatistic().visit().getViolations() );
        assertEquals( 1, new CyclomaticComplexityStatistic().visit().getViolations() );
        assertEquals( 2, getNumberOfClasses() );
    }

    private int getNumberOfMethodsInProject() {
        return new MethodCountVisitor( new JavaModelPath( project ) ).visit().getMethodCount();
    }

    private int getNumberOfClassesInProject() {
        return new ClassCountVisitor( new JavaModelPath( project ) ).visit().getClassCount();
    }
}
