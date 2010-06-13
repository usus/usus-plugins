package org.projectusus.core.internal.proportions.rawdata;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.projectusus.core.IMetricsAccessor;
import org.projectusus.core.basis.CodeProportionKind;
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
        IMetricsAccessor metrics = UsusCorePlugin.getMetricsAccessor();
        assertEquals( 0, getNumberOfClassesInProject() );
        assertEquals( 0, getNumberOfMethodsInProject() );
        assertEquals( 0, metrics.getViolationCount( project, CodeProportionKind.KG ) );
        assertEquals( 0, metrics.getViolationCount( project, CodeProportionKind.ML ) );
        assertEquals( 0, metrics.getViolationCount( project, CodeProportionKind.CC ) );
        assertEquals( 0, getNumberOfClasses() );
    }

    private void computeFile1AndCheckPreconditions() throws Exception {
        createFileAndBuild( "Reset" + "1" );

        IMetricsAccessor metrics = UsusCorePlugin.getMetricsAccessor();
        assertEquals( 1, getNumberOfClassesInProject() );
        assertEquals( 0, metrics.getViolationCount( project, CodeProportionKind.KG ) );
        assertEquals( 2, getNumberOfMethodsInProject() );
        assertEquals( 1, metrics.getViolationCount( project, CodeProportionKind.ML ) );
        assertEquals( 1, metrics.getViolationCount( project, CodeProportionKind.CC ) );
        assertEquals( 1, getNumberOfClasses() );
    }

    private void computeFiles2AndCheckPreconditions() throws Exception {
        createFile( "Reset" + "2" );
        createFileAndBuild( "Reset" + "1" );

        IMetricsAccessor metrics = UsusCorePlugin.getMetricsAccessor();
        assertEquals( 2, getNumberOfClassesInProject() );
        assertEquals( 0, metrics.getViolationCount( project, CodeProportionKind.KG ) );
        assertEquals( 3, getNumberOfMethodsInProject() );
        assertEquals( 1, metrics.getViolationCount( project, CodeProportionKind.ML ) );
        assertEquals( 1, metrics.getViolationCount( project, CodeProportionKind.CC ) );
        assertEquals( 2, getNumberOfClasses() );
    }

    private int getNumberOfMethodsInProject() {
        return new MethodCountVisitor( new JavaModelPath( project ) ).getMethodCount();
    }

    private int getNumberOfClassesInProject() {
        return new ClassCountVisitor( new JavaModelPath( project ) ).getClassCount();
    }
}
