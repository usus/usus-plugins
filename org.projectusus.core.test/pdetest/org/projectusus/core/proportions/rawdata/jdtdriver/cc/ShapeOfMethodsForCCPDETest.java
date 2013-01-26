package org.projectusus.core.proportions.rawdata.jdtdriver.cc;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.dom.InfixExpression.Operator;
import org.junit.Test;
import org.projectusus.core.metrics.MetricsCollector;
import org.projectusus.core.proportions.rawdata.jdtdriver.JavaFileDriver;
import org.projectusus.core.statistics.test.PDETestForMetricsComputation;

import com.google.common.collect.Multimap;

public class ShapeOfMethodsForCCPDETest extends PDETestForMetricsComputation {

    @Test
    public void assumptionsRegardingAbstractAndConcreteTypesAreValid() throws Exception {
        project.createFolder( "cc" );
        IFile file = createJavaFile( "cc/CC.java" );

        CCInspector inspector = new CCInspector();

        new JavaFileDriver( file ).compute( createSetWith( inspector ) );

        Multimap<String, ASTNodeTypes> map = inspector.getMap();

        validateEmpty( map.get( "empty" ) );
        validateOneWhile( map.get( "oneWhile" ) );
        validateThreeCase( map.get( "threeCase" ) );
        validateTwoLogical( map.get( "twoLogical" ) );
        validateTwoBitwise( map.get( "twoBitwise" ) );
        validateTwoIfOneElse( map.get( "twoIfOneElse" ) );
        validateOneForeach( map.get( "oneForeach" ) );
        validateOneFor( map.get( "oneFor" ) );
        validateOneDo( map.get( "oneDo" ) );
        validateOneConditional( map.get( "oneConditional" ) );
        validateConditionalAnd_conditionalOr( map.get( "conditionalAnd_conditionalOr" ) );
        validateThreeConditionalAnd( map.get( "threeConditionalAnd" ) );
        validateOneTryCatch( map.get( "oneTryCatch" ) );
        validateOuterMethod( map.get( "outerMethod" ) );
        validateInnerMethod( map.get( "innerMethod" ) );
        validateInitializerWithConditional( map.get( "initializer" ) );
    }

    private void validateEmpty( Collection<ASTNodeTypes> collection ) {
        assertThat( collection.size(), is( 0 ) );
    }

    private void validateOneWhile( Collection<ASTNodeTypes> collection ) {
        assertThat( collection.size(), is( 1 ) );
        assertThat( collection.iterator().next(), is( instanceOf( WhileStatementType.class ) ) );
    }

    private void validateThreeCase( Collection<ASTNodeTypes> collection ) {
        assertThat( collection.size(), is( 3 ) );
        Iterator<ASTNodeTypes> iterator = collection.iterator();
        assertThat( iterator.next(), is( instanceOf( SwitchCaseType.class ) ) );
        assertThat( iterator.next(), is( instanceOf( SwitchCaseType.class ) ) );
        assertThat( iterator.next(), is( instanceOf( SwitchCaseType.class ) ) );
    }

    private void validateTwoLogical( Collection<ASTNodeTypes> collection ) {
        assertThat( collection.size(), is( 0 ) );
    }

    private void validateTwoBitwise( Collection<ASTNodeTypes> collection ) {
        assertThat( collection.size(), is( 0 ) );
    }

    private void validateTwoIfOneElse( Collection<ASTNodeTypes> collection ) {
        assertThat( collection.size(), is( 2 ) );
        Iterator<ASTNodeTypes> iterator = collection.iterator();
        assertThat( iterator.next(), is( instanceOf( IfStatementType.class ) ) );
        assertThat( iterator.next(), is( instanceOf( IfStatementType.class ) ) );
    }

    private void validateOneForeach( Collection<ASTNodeTypes> collection ) {
        assertThat( collection.size(), is( 1 ) );
        assertThat( collection.iterator().next(), is( instanceOf( EnhancedForStatementType.class ) ) );
    }

    private void validateOneFor( Collection<ASTNodeTypes> collection ) {
        assertThat( collection.size(), is( 1 ) );
        assertThat( collection.iterator().next(), is( instanceOf( ForStatementType.class ) ) );
    }

    private void validateOneDo( Collection<ASTNodeTypes> collection ) {
        assertThat( collection.size(), is( 1 ) );
        assertThat( collection.iterator().next(), is( instanceOf( DoStatementType.class ) ) );
    }

    private void validateOneConditional( Collection<ASTNodeTypes> collection ) {
        assertThat( collection.size(), is( 1 ) );
        assertThat( collection.iterator().next(), is( instanceOf( ConditionalExpressionType.class ) ) );
    }

    private void validateConditionalAnd_conditionalOr( Collection<ASTNodeTypes> collection ) {
        assertThat( collection.size(), is( 2 ) );
        Iterator<ASTNodeTypes> iterator = collection.iterator();
        validateInfixExpression( iterator.next(), Operator.CONDITIONAL_OR, 0 );
        validateInfixExpression( iterator.next(), Operator.CONDITIONAL_AND, 0 );
    }

    private void validateThreeConditionalAnd( Collection<ASTNodeTypes> collection ) {
        assertThat( collection.size(), is( 1 ) );
        validateInfixExpression( collection.iterator().next(), Operator.CONDITIONAL_AND, 2 );
    }

    private void validateOneTryCatch( Collection<ASTNodeTypes> collection ) {
        assertThat( collection.size(), is( 1 ) );
        assertThat( collection.iterator().next(), is( instanceOf( CatchClauseType.class ) ) );
    }

    private void validateOuterMethod( Collection<ASTNodeTypes> collection ) {
        assertThat( collection.size(), is( 0 ) );
    }

    private void validateInnerMethod( Collection<ASTNodeTypes> collection ) {
        assertThat( collection.size(), is( 0 ) );
    }

    private void validateInitializerWithConditional( Collection<ASTNodeTypes> collection ) {
        assertThat( collection.size(), is( 1 ) );
        assertThat( collection.iterator().next(), is( instanceOf( ConditionalExpressionType.class ) ) );
    }

    private void validateInfixExpression( ASTNodeTypes node, Operator operator, int extendedCount ) {
        assertThat( node, is( instanceOf( InfixExpressionType.class ) ) );
        assertThat( node.getOperator(), is( operator ) );
        assertThat( node.getExtendedOperatorCount(), is( extendedCount ) );
    }

    private Set<MetricsCollector> createSetWith( MetricsCollector inspector ) {
        Set<MetricsCollector> set = new HashSet<MetricsCollector>();
        set.add( inspector );
        return set;
    }
}
