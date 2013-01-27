package org.projectusus.core.proportions.rawdata.jdtdriver.classes;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.junit.Test;
import org.projectusus.core.metrics.MetricsCollector;
import org.projectusus.core.proportions.rawdata.jdtdriver.JavaFileDriver;
import org.projectusus.core.statistics.test.PDETestForMetricsComputation;

public class ClassPDETest extends PDETestForMetricsComputation {

    @Test
    public void assumptionsRegardingClassesAreValid() throws Exception {
        project.createFolder( "classes" );
        IFile file = createJavaFile( "classes/Classes.java" );

        ClassInspector inspector = new ClassInspector();

        new JavaFileDriver( file ).compute( createSetWith( inspector ) );

        List<String> collection = inspector.getClassnames();

        assertThat( collection.size(), is( 10 ) );
        assertThat( collection.get( 0 ), is( "Classes" ) );
        assertThat( collection.get( 1 ), is( "ClassWithAnon" ) );
        assertThat( collection.get( 2 ), is( "ClassWithInner" ) );
        assertThat( collection.get( 3 ), is( "InnerClass" ) );
        assertThat( collection.get( 4 ), is( "ClassWithStaticInner" ) );
        assertThat( collection.get( 5 ), is( "StaticInnerClass" ) );
        assertThat( collection.get( 6 ), is( "Interface" ) );
        assertThat( collection.get( 7 ), is( "AbstractClass" ) );
        assertThat( collection.get( 8 ), is( "Enum" ) );
        assertThat( collection.get( 9 ), is( "AnnotationClass" ) );
    }

    private Set<MetricsCollector> createSetWith( MetricsCollector inspector ) {
        Set<MetricsCollector> set = new HashSet<MetricsCollector>();
        set.add( inspector );
        return set;
    }
}
