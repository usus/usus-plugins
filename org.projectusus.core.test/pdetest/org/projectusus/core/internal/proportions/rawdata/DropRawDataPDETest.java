package org.projectusus.core.internal.proportions.rawdata;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.statistics.visitors.ClassCountVisitor;
import org.projectusus.core.statistics.visitors.MethodCountVisitor;
import org.projectusus.statistics.ClassSizeStatistic;
import org.projectusus.statistics.CyclomaticComplexityStatistic;
import org.projectusus.statistics.MethodLengthStatistic;

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
        assertEquals( 0, getVisitedClassSizeStatistic().getViolations() );
        assertEquals( 0, getVisitedMethodLengthStatistic().getViolations() );
        assertEquals( 0, getVisitedCCStatistic().getViolations() );
        assertEquals( 0, getNumberOfClasses() );
    }

    private CyclomaticComplexityStatistic getVisitedCCStatistic() {
        CyclomaticComplexityStatistic r = new CyclomaticComplexityStatistic();
        r.visit();
        return r;
    }

    private MethodLengthStatistic getVisitedMethodLengthStatistic() {
        MethodLengthStatistic r = new MethodLengthStatistic();
        r.visit();
        return r;
    }

    private ClassSizeStatistic getVisitedClassSizeStatistic() {
        ClassSizeStatistic r = new ClassSizeStatistic();
        r.visit();
        return r;
    }

    private void computeFile1AndCheckPreconditions() throws Exception {
        createFileAndBuild( "Reset" + "1" );

        assertEquals( 1, getNumberOfClassesInProject() );
        assertEquals( 0, getVisitedClassSizeStatistic().getViolations() );
        assertEquals( 2, getNumberOfMethodsInProject() );
        assertEquals( 1, getVisitedMethodLengthStatistic().getViolations() );
        assertEquals( 1, getVisitedCCStatistic().getViolations() );
        assertEquals( 1, getNumberOfClasses() );
    }

    private void computeFiles2AndCheckPreconditions() throws Exception {
        createFile( "Reset" + "2" );
        createFileAndBuild( "Reset" + "1" );

        assertEquals( 2, getNumberOfClassesInProject() );
        assertEquals( 0, getVisitedClassSizeStatistic().getViolations() );
        assertEquals( 3, getNumberOfMethodsInProject() );
        assertEquals( 1, getVisitedMethodLengthStatistic().getViolations() );
        assertEquals( 1, getVisitedCCStatistic().getViolations() );
        assertEquals( 2, getNumberOfClasses() );
    }

    private int getNumberOfMethodsInProject() {
        return new MethodCountVisitor( new JavaModelPath( project ) ).visitAndReturn().getMethodCount();
    }

    private int getNumberOfClassesInProject() {
        return new ClassCountVisitor( new JavaModelPath( project ) ).visitAndReturn().getClassCount();
    }
}
