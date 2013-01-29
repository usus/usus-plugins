package org.projectusus.core.proportions.rawdata.jdtdriver.classes;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.junit.Test;
import org.projectusus.core.proportions.rawdata.jdtdriver.JavaFileDriver;
import org.projectusus.core.statistics.test.PDETestForMetricsComputation;

public class Class_PDETest extends PDETestForMetricsComputation {

    @Test
    public void assumptionsAreValid() throws Exception {
        project.createFolder( "pde" );
        IFile file = createJavaFile( "pde/Classes.java" );

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
}
