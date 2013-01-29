package org.projectusus.core.proportions.rawdata.jdtdriver.trainwreck;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.junit.Test;
import org.projectusus.core.proportions.rawdata.jdtdriver.JavaFileDriver;
import org.projectusus.core.statistics.test.PDETestForMetricsComputation;

public class TrainWreck_PDETest extends PDETestForMetricsComputation {

    @Test
    public void assumptionsAreValid() throws Exception {
        project.createFolder( "pde" );
        IFile file = createJavaFile( "pde/TrainWreck.java" );

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
}
