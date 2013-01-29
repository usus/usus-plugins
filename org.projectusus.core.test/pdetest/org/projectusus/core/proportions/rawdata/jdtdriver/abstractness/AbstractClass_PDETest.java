package org.projectusus.core.proportions.rawdata.jdtdriver.abstractness;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.eclipse.core.resources.IFile;
import org.junit.Test;
import org.projectusus.core.proportions.rawdata.jdtdriver.JavaFileDriver;
import org.projectusus.core.statistics.test.PDETestForMetricsComputation;

public class AbstractClass_PDETest extends PDETestForMetricsComputation {

    @Test
    public void assumptionsAreValid() throws Exception {
        project.createFolder( "pde" );
        IFile file = createJavaFile( "pde/Abstractness.java" );

        AbstractClassInspector inspector = new AbstractClassInspector();

        new JavaFileDriver( file ).compute( createSetWith( inspector ) );

        assertThat( inspector.getAbstractCount(), is( 2 ) );
        assertThat( inspector.getConcreteCount(), is( 3 ) );
    }
}
