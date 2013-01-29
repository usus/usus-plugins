package org.projectusus.core.proportions.rawdata.jdtdriver.cc;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.eclipse.core.resources.IFile;
import org.junit.Test;
import org.projectusus.core.proportions.rawdata.jdtdriver.JavaFileDriver;
import org.projectusus.core.statistics.test.PDETestForMetricsComputation;

import com.google.common.collect.Multimap;

public class ShapeOfMethodsForCCPDETest extends PDETestForMetricsComputation {

    @Test
    public void assumptionsAreValid() throws Exception {
        project.createFolder( "cc" );
        IFile file = createJavaFile( "cc/CC.java" );

        CCInspector inspector = new CCInspector();

        new JavaFileDriver( file ).compute( createSetWith( inspector ) );

        assertThat( inspector.getNames(), hasSize( 16 ) );

        Multimap<String, String> map = inspector.getMap();
        assertThat( map.get( "empty" ), is( empty() ) );
        assertThat( map.get( "oneWhile" ), contains( "WhileStatement" ) );
        assertThat( map.get( "threeCase" ), contains( "SwitchCase", "SwitchCase", "SwitchCase" ) );
        assertThat( map.get( "twoLogical" ), is( empty() ) );
        assertThat( map.get( "twoBitwise" ), is( empty() ) );
        assertThat( map.get( "twoIfOneElse" ), contains( "IfStatement", "IfStatement" ) );
        assertThat( map.get( "oneForeach" ), contains( "EnhancedForStatement" ) );
        assertThat( map.get( "oneFor" ), contains( "ForStatement" ) );
        assertThat( map.get( "oneDo" ), contains( "DoStatement" ) );
        assertThat( map.get( "oneConditional" ), contains( "ConditionalExpression" ) );
        assertThat( map.get( "conditionalAnd_conditionalOr" ), contains( "InfixExpression", "InfixExpression" ) );
        assertThat( map.get( "threeConditionalAnd" ), contains( "InfixExpression 3" ) );
        assertThat( map.get( "oneTryCatch" ), contains( "CatchClause" ) );
        assertThat( map.get( "outerMethod" ), is( empty() ) );
        assertThat( map.get( "innerMethod" ), is( empty() ) );
        assertThat( map.get( "initializer" ), contains( "ConditionalExpression" ) );
    }
}
