package org.projectusus.core.proportions.rawdata.jdtdriver.test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.junit.Test;
import org.projectusus.core.metrics.MetricsCollector;
import org.projectusus.core.proportions.rawdata.jdtdriver.JavaFileDriver;
import org.projectusus.core.statistics.test.PDETestForMetricsComputation;

public class ShapeOfClassesAndInterfacesPDETest extends PDETestForMetricsComputation {

    @Test
    public void assumptionsRegardingAbstractAndConcreteTypesAreValid() throws Exception {
        IFile file = setupWorkspace( "Abstractness.java" );

        AbstractClassInspector inspector = new AbstractClassInspector();

        new JavaFileDriver( file ).compute( createSetWith( inspector ) );

        assertThat( inspector.getAbstractCount(), is( 2 ) );
        assertThat( inspector.getConcreteCount(), is( 3 ) );
    }

    private Set<MetricsCollector> createSetWith( AbstractClassInspector inspector ) {
        Set<MetricsCollector> set = new HashSet<MetricsCollector>();
        set.add( inspector );
        return set;
    }

    private IFile setupWorkspace( String filename ) throws CoreException, Exception {
        return setupWorkspace( "abstractness", filename );
    }

    private IFile setupWorkspace( String packagename, String filename ) throws CoreException, Exception {
        project.createFolder( packagename );
        IFile file = createJavaFile( packagename + "/" + filename );
        workspace.buildFullyAndWait();
        return file;
    }
}
