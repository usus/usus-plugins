package org.projectusus.core.proportions.rawdata.jdtdriver.acd;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.eclipse.core.resources.IFile;
import org.junit.Test;
import org.projectusus.core.proportions.rawdata.jdtdriver.JavaFileDriver;
import org.projectusus.core.statistics.test.PDETestForMetricsComputation;

import com.google.common.collect.Multimap;

public class ACD_PDETest extends PDETestForMetricsComputation {

    @Test
    public void assumptionsAreValid() throws Exception {
        project.createFolder( "acd" );
        IFile file = createJavaFile( "acd/Acd.java" );

        ACDInspector inspector = new ACDInspector();

        new JavaFileDriver( file ).compute( createSetWith( inspector ) );

        // assertThat( inspector.getTypes(), hasSize( 5 ) );

        Multimap<String, String> map = inspector.getTypeConnections();

        assertThat( map.get( "acd/Acd" ), is( empty() ) );
        assertThat( map.get( "acd/Acd2" ), is( empty() ) );
        assertThat( map.get( "acd/Acd3" ), is( empty() ) );
        assertThat( map.get( "acd/Acd3Helper" ), contains( "acd/Acd3", "acd/Acd3" ) );
        assertThat( map.get( "acd/Acd4" ), contains( "acd/Acd4Helper", "acd/Acd4Helper" ) );
        assertThat( map.get( "acd/Acd4Helper" ), contains( "acd/Acd4", "acd/Acd4" ) );

        // assertThat( map.get( "empty" ), is( empty() ) );
        // assertThat( map.get( "oneWhile" ), contains( "WhileStatement" ) );
        // assertThat( map.get( "threeCase" ), contains( "SwitchCase", "SwitchCase", "SwitchCase" ) );
        // assertThat( map.get( "twoLogical" ), is( empty() ) );
        // assertThat( map.get( "twoBitwise" ), is( empty() ) );
        // assertThat( map.get( "twoIfOneElse" ), contains( "IfStatement", "IfStatement" ) );
        // assertThat( map.get( "oneForeach" ), contains( "EnhancedForStatement" ) );
        // assertThat( map.get( "oneFor" ), contains( "ForStatement" ) );
        // assertThat( map.get( "oneDo" ), contains( "DoStatement" ) );
        // assertThat( map.get( "oneConditional" ), contains( "ConditionalExpression" ) );
        // assertThat( map.get( "conditionalAnd_conditionalOr" ), contains( "InfixExpression", "InfixExpression" ) );
        // assertThat( map.get( "threeConditionalAnd" ), contains( "InfixExpression 3" ) );
        // assertThat( map.get( "oneTryCatch" ), contains( "CatchClause" ) );
        // assertThat( map.get( "outerMethod" ), is( empty() ) );
        // assertThat( map.get( "innerMethod" ), is( empty() ) );
        // assertThat( map.get( "initializer" ), contains( "ConditionalExpression" ) );
    }
}
