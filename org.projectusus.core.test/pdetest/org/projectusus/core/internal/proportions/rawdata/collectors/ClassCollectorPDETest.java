package org.projectusus.core.internal.proportions.rawdata.collectors;

import static org.junit.Assert.assertEquals;

import org.eclipse.core.resources.IFile;
import org.junit.Test;
import org.projectusus.core.basis.CodeProportionUnit;
import org.projectusus.core.internal.proportions.rawdata.PDETestForMetricsComputation;

public class ClassCollectorPDETest extends PDETestForMetricsComputation {

    @Test
    public void oneClass() throws Exception {
        createFileAndBuild( "_one" );
        assertEquals( 1, model.getNumberOf( CodeProportionUnit.CLASS ) );
    }

    @Test
    public void twoClasses() throws Exception {
        createFileAndBuild( "_two" );
        assertEquals( 2, model.getNumberOf( CodeProportionUnit.CLASS ) );
    }

    @Test
    public void oneWithInnerClass() throws Exception {
        createFileAndBuild( "_inner" );
        assertEquals( 2, model.getNumberOf( CodeProportionUnit.CLASS ) );
    }

    @Test
    public void oneWithStaticInnerClass() throws Exception {
        createFileAndBuild( "_static" );
        assertEquals( 2, model.getNumberOf( CodeProportionUnit.CLASS ) );
    }

    @Test
    public void oneWithAnonymousClass() throws Exception {
        createFileAndBuild( "_anon" );
        assertEquals( 1, model.getNumberOf( CodeProportionUnit.CLASS ) );
    }

    protected IFile createFileAndBuild( String filenumber ) throws Exception {
        return super.createFileAndBuild( "Class" + filenumber );
    }
}
