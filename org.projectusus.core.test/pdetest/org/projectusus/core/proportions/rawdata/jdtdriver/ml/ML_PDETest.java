package org.projectusus.core.proportions.rawdata.jdtdriver.ml;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.junit.Test;
import org.projectusus.core.metrics.MetricsCollector;
import org.projectusus.core.proportions.rawdata.jdtdriver.JavaFileDriver;
import org.projectusus.core.statistics.test.PDETestForMetricsComputation;

import com.google.common.collect.Multimap;

public class ML_PDETest extends PDETestForMetricsComputation {

    @Test
    public void assumptionsRegardingMLAreValid() throws Exception {
        project.createFolder( "ml" );
        IFile file = createJavaFile( "ml/ML.java" );

        MLInspector inspector = new MLInspector();

        new JavaFileDriver( file ).compute( createSetWith( inspector ) );

        Multimap<String, Integer> map = inspector.getValues();

        assertThat( map.keySet().size(), is( 22 ) );

        assertThat( map.get( "initializer" ), contains( 1 ) );
        assertThat( map.get( "static initializer" ), contains( 1 ) );
        assertThat( map.get( "empty" ), contains( 0 ) );
        assertThat( map.get( "ite" ), contains( 1, 0, 0 ) );

        // TODO Bug!!
        assertThat( map.get( "ite_single" ), contains( 1 ) );

        assertThat( map.get( "assign" ), contains( 1 ) );
        assertThat( map.get( "iteassign" ), contains( 1, 1, 1 ) );
        assertThat( map.get( "anon" ), contains( 1 ) );

        assertThat( map.get( "anonWithMeth" ), contains( 1 ) );
        assertThat( map.get( "anonWithMeth_1" ), contains( 0 ) );

        assertThat( map.get( "anonWith3VarsBefore" ), contains( 4 ) );

        assertThat( map.get( "anonWithMethAnd3VarsBefore" ), contains( 3, 1, 1 ) );
        assertThat( map.get( "anonWithMethAnd3VarsBefore_1" ), contains( 0 ) );

        assertThat( map.get( "anonWith3VarsAfter" ), contains( 4 ) );

        assertThat( map.get( "anonWithMethAnd3VarsAfter" ), contains( 3, 1, 1 ) );
        assertThat( map.get( "anonWithMethAnd3VarsAfter_1" ), contains( 0 ) );

        assertThat( map.get( "anonWith4Meth" ), contains( 3 ) );
        assertThat( map.get( "anonWith4Meth_1" ), contains( 1 ) );
        assertThat( map.get( "anonWith4Meth_2" ), contains( 1 ) );
        assertThat( map.get( "anonWith4Meth_3" ), contains( 1 ) );
        assertThat( map.get( "anonWith4Meth_4" ), contains( 1 ) );

        assertThat( map.get( "assignInML2" ), contains( 1 ) );
        assertThat( map.get( "m1" ), is( empty() ) );
        assertThat( map.get( "m2" ), is( empty() ) );

    }

    private Set<MetricsCollector> createSetWith( MetricsCollector inspector ) {
        Set<MetricsCollector> set = new HashSet<MetricsCollector>();
        set.add( inspector );
        return set;
    }
}
