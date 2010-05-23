package org.projectusus.core.internal.proportions.rawdata;

import static org.junit.Assert.assertEquals;

import org.eclipse.core.resources.IProject;
import org.junit.Test;
import org.projectusus.core.IMetricsAccessor;
import org.projectusus.core.basis.CodeProportionKind;
import org.projectusus.core.basis.CodeProportionUnit;
import org.projectusus.core.internal.UsusCorePlugin;

public class DropRawDataPDETest extends PDETestForMetricsComputation {

    @Test
    public void dropProjectWithFile1() throws Exception {
        computeFile1AndCheckPreconditions();

        UsusCorePlugin.getUsusModelForAdapter().dropRawData( project );

        checkProjectRawDataIsEmpty1File( project );
    }

    @Test
    public void dropProjectWithFiles2() throws Exception {
        computeFiles2AndCheckPreconditions();

        UsusCorePlugin.getUsusModelForAdapter().dropRawData( project );

        checkProjectRawDataIsEmpty2Files( project );
    }

    private void checkProjectRawDataIsEmpty1File( IProject project ) {
        IMetricsAccessor metrics = UsusCorePlugin.getMetricsAccessor();
        assertEquals( 0, metrics.getNumberOf( project, CodeProportionUnit.CLASS ) );
        assertEquals( 0, metrics.getNumberOf( project, CodeProportionUnit.METHOD ) );
        assertEquals( 0, metrics.getViolationCount( project, CodeProportionKind.KG ) );
        assertEquals( 0, metrics.getViolationCount( project, CodeProportionKind.ML ) );
        assertEquals( 0, metrics.getViolationCount( project, CodeProportionKind.CC ) );
        assertEquals( 0, getClassCount() );
    }

    private void checkProjectRawDataIsEmpty2Files( IProject project ) {
        IMetricsAccessor metrics = UsusCorePlugin.getMetricsAccessor();
        assertEquals( 0, metrics.getNumberOf( project, CodeProportionUnit.CLASS ) );
        assertEquals( 0, metrics.getNumberOf( project, CodeProportionUnit.METHOD ) );
        assertEquals( 0, metrics.getViolationCount( project, CodeProportionKind.KG ) );
        assertEquals( 0, metrics.getViolationCount( project, CodeProportionKind.ML ) );
        assertEquals( 0, metrics.getViolationCount( project, CodeProportionKind.CC ) );
        assertEquals( 0, getClassCount() );
    }

    private void computeFile1AndCheckPreconditions() throws Exception {
        createFileAndBuild( "Reset" + "1" );

        IMetricsAccessor metrics = UsusCorePlugin.getMetricsAccessor();
        assertEquals( 1, metrics.getNumberOf( project, CodeProportionUnit.CLASS ) );
        assertEquals( 0, metrics.getViolationCount( project, CodeProportionKind.KG ) );
        assertEquals( 2, metrics.getNumberOf( project, CodeProportionUnit.METHOD ) );
        assertEquals( 1, metrics.getViolationCount( project, CodeProportionKind.ML ) );
        assertEquals( 1, metrics.getViolationCount( project, CodeProportionKind.CC ) );
        assertEquals( 1, getClassCount() );
    }

    private void computeFiles2AndCheckPreconditions() throws Exception {
        createFile( "Reset" + "2" );
        createFileAndBuild( "Reset" + "1" );

        IMetricsAccessor metrics = UsusCorePlugin.getMetricsAccessor();
        assertEquals( 2, metrics.getNumberOf( project, CodeProportionUnit.CLASS ) );
        assertEquals( 0, metrics.getViolationCount( project, CodeProportionKind.KG ) );
        assertEquals( 3, metrics.getNumberOf( project, CodeProportionUnit.METHOD ) );
        assertEquals( 1, metrics.getViolationCount( project, CodeProportionKind.ML ) );
        assertEquals( 1, metrics.getViolationCount( project, CodeProportionKind.CC ) );
        assertEquals( 2, getClassCount() );
    }

    private int getClassCount() {
        return UsusCorePlugin.getMetricsAccessor().getNumberOf( CodeProportionUnit.CLASS );
    }
}
