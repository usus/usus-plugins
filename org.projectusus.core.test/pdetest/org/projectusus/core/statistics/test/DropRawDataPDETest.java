package org.projectusus.core.statistics.test;

import static org.junit.Assert.assertEquals;
import static org.projectusus.core.statistics.UsusModelProvider.ususModelForAdapter;

import org.junit.Test;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.statistics.visitors.ClassCountVisitor;
import org.projectusus.core.statistics.visitors.MethodCountVisitor;
import org.projectusus.metrics.util.ClassSizeVisitor;
import org.projectusus.metrics.util.CyclomaticComplexityVisitor;
import org.projectusus.metrics.util.MethodLengthVisitor;

public class DropRawDataPDETest extends PDETestForMetricsComputation {

    @Test
    public void dropProjectWithFile1() throws Exception {
        computeFile1AndCheckPreconditions();
        ususModelForAdapter().dropRawData( project.get() );
        checkProjectRawDataIsEmpty();
    }

    @Test
    public void dropProjectWithFiles2() throws Exception {
        computeFiles2AndCheckPreconditions();
        ususModelForAdapter().dropRawData( project.get() );
        checkProjectRawDataIsEmpty();
    }

    private void checkProjectRawDataIsEmpty() {
        assertEquals( 0, getNumberOfClassesInProject() );
        assertEquals( 0, getNumberOfMethodsInProject() );
        assertEquals( 0, getClassSizeSum() );
        assertEquals( 0, getMethodLengthSum() );
        assertEquals( 0, getCCSum() );
        assertEquals( 0, getNumberOfClasses() );
    }

    private int getCCSum() {
        CyclomaticComplexityVisitor r = new CyclomaticComplexityVisitor();
        r.visit();
        return r.getCC();
    }

    private int getMethodLengthSum() {
        MethodLengthVisitor r = new MethodLengthVisitor();
        r.visit();
        return r.getML();
    }

    private int getClassSizeSum() {
        ClassSizeVisitor r = new ClassSizeVisitor();
        r.visit();
        return r.getClassSizeSum();
    }

    private void computeFile1AndCheckPreconditions() throws Exception {
        createFileAndBuild( "Reset1" );

        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, getNumberOfClassesInProject() );
        assertEquals( 2, getNumberOfMethodsInProject() );
        assertEquals( 2, getClassSizeSum() );
        assertEquals( 22, getMethodLengthSum() );
        assertEquals( 8, getCCSum() );
    }

    private void computeFiles2AndCheckPreconditions() throws Exception {
        createFile( "Reset2" );
        createFileAndBuild( "Reset1" );

        assertEquals( 2, getNumberOfClasses() );
        assertEquals( 2, getNumberOfClassesInProject() );
        assertEquals( 3, getNumberOfMethodsInProject() );
        assertEquals( 3, getClassSizeSum() );
        assertEquals( 23, getMethodLengthSum() );
        assertEquals( 9, getCCSum() );
    }

    private int getNumberOfMethodsInProject() {
        return new MethodCountVisitor( new JavaModelPath( project.get() ) ).visitAndReturn().getMethodCount();
    }

    private int getNumberOfClassesInProject() {
        return new ClassCountVisitor( new JavaModelPath( project.get() ) ).visitAndReturn().getClassCount();
    }
}
