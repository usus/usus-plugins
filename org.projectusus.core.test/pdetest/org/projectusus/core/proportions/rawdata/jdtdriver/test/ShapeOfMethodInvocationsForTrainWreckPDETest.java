package org.projectusus.core.proportions.rawdata.jdtdriver.test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.junit.Test;
import org.projectusus.core.metrics.MetricsCollector;
import org.projectusus.core.proportions.rawdata.jdtdriver.JavaFileDriver;
import org.projectusus.core.statistics.test.PDETestForMetricsComputation;

public class ShapeOfMethodInvocationsForTrainWreckPDETest extends PDETestForMetricsComputation {

    @Test
    public void assumptionsRegardingMethodInvocationsAreValid() throws Exception {
        project.createFolder( "trainwreck" );
        IFile file = createJavaFile( "trainwreck/TrainWreck.java" );

        TrainWreckInspector inspector = new TrainWreckInspector();

        new JavaFileDriver( file ).compute( createSetWith( inspector ) );

        Map<String, String> map = inspector.getMap();

        validateEmpty( map.get( "empty" ) );
        validateOneMethodCall( map.get( "oneMethodCall" ) );
        validateThreeMethods( map.get( "threeMethods" ) );
        validateTwoStatements( map.get( "twoStatements" ) );
        validateTwoMethodsWithArguments( map.get( "twoMethodsWithArguments" ) );
        validateAssignment( map.get( "assignment" ) );
    }

    private void validateEmpty( String invokedNodes ) {
        assertThat( invokedNodes, is( "" ) );
    }

    private void validateOneMethodCall( String invokedNodes ) {
        assertThat( invokedNodes, is( "MI a # " ) );
    }

    private void validateThreeMethods( String invokedNodes ) {
        assertThat( invokedNodes, is( "MI x on MI b on MI a # " ) );
    }

    private void validateTwoStatements( String invokedNodes ) {
        assertThat( invokedNodes, is( "MI b on MI a # MI y on MI x # " ) );
    }

    private void validateTwoMethodsWithArguments( String invokedNodes ) {
        assertThat( invokedNodes, is( "MI b on MI a # MI y on MI x # MI w on MI z # " ) );
    }

    private void validateAssignment( String invokedNodes ) {
        assertThat( invokedNodes, is( "MI b on MI a # MI y on MI x # " ) );
    }

    private Set<MetricsCollector> createSetWith( MetricsCollector inspector ) {
        Set<MetricsCollector> set = new HashSet<MetricsCollector>();
        set.add( inspector );
        return set;
    }
}
