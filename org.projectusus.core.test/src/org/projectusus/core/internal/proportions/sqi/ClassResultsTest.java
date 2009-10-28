package org.projectusus.core.internal.proportions.sqi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class ClassResultsTest {

    private final String PACKAGENAME = "package.name";
    private final String CLASSNAME = "ClassName";
    private final String METHODNAME1 = "methodname1";
    private final String METHODNAME2 = "methodname2";
    private final int CLASSLINENO = 77;
    private final int METHOD1LINENO = 99;
    private final int METHOD2LINENO = 133;

    private ClassResults classResults;
    private BetterDetailAST methodAST1;
    private BetterDetailAST methodAST2;
    
    
    @Before
    public void setup(){
        classResults = new ClassResults( PACKAGENAME, CLASSNAME, CLASSLINENO );
        methodAST1 = createMethodAST( METHODNAME1, METHOD1LINENO);
        methodAST2 = createMethodAST(METHODNAME2, METHOD2LINENO);
    }

    private BetterDetailAST createMethodAST( String methodName, int lineno) {
        BetterDetailAST ast = mock( BetterDetailAST.class );
        when(new Integer(ast.getType())).thenReturn(new Integer(TokenTypes.METHOD_DEF));
        when(new Integer(ast.getLineNo())).thenReturn( new Integer(lineno) );
        BetterDetailAST identToken = mock(BetterDetailAST.class);
        when(identToken.getText()).thenReturn( methodName );
        when(ast.findFirstToken( TokenTypes.IDENT )).thenReturn( identToken );
        return ast;
    }
    
    @Test
    public void className(){
        assertEquals( CLASSNAME, classResults.getClassName() );
    }
    
    @Test
    public void classLineNo(){
        assertEquals( CLASSLINENO, classResults.getLineNo() );
    }
    
    @Test
    public void packageName(){
        assertEquals( PACKAGENAME, classResults.getPackageName() );
    }
    
    @Test
    public void violationBasisNoMethods(){
        assertEquals( 1, classResults.getViolationBasis( IsisMetrics.KG ) );
        assertEquals( 0, classResults.getViolationBasis( IsisMetrics.CC ) );
        assertEquals( 0, classResults.getViolationBasis( IsisMetrics.ML ) );
    }
    @Test
    public void violationBasis1Method(){
        classResults.setCCResult( methodAST1, 0 );
        assertEquals( 1, classResults.getViolationBasis( IsisMetrics.KG ) );
        assertEquals( 1, classResults.getViolationBasis( IsisMetrics.CC ) );
        assertEquals( 1, classResults.getViolationBasis( IsisMetrics.ML ) );
    }
    @Test
    public void violationBasis2Methods(){
        classResults.setCCResult( methodAST1, 0 );
        classResults.setCCResult( methodAST2, 0 );
        assertEquals( 1, classResults.getViolationBasis( IsisMetrics.KG ) );
        assertEquals( 2, classResults.getViolationBasis( IsisMetrics.CC ) );
        assertEquals( 2, classResults.getViolationBasis( IsisMetrics.ML ) );
    }
    
    @Test
    public void violationCountNoMethods(){
        assertEquals(0, classResults.getViolationCount( IsisMetrics.KG ));
        assertEquals(0, classResults.getViolationCount( IsisMetrics.CC ));
        assertEquals(0, classResults.getViolationCount( IsisMetrics.ML ));
    }
    
    @Test
    public void violationCountCC1Method(){
        int value = 6;
        classResults.setCCResult( methodAST1, value );
        assertEquals( 0, classResults.getViolationCount( IsisMetrics.KG ) );
        assertEquals( 1, classResults.getViolationCount( IsisMetrics.CC ) );
        assertEquals( 0, classResults.getViolationCount( IsisMetrics.ML ) );
    }
    @Test
    public void violationCountCC2Methods(){
        int value = 6;
        classResults.setCCResult( methodAST1, value );
        classResults.setCCResult( methodAST2, value );
        assertEquals( 0, classResults.getViolationCount( IsisMetrics.KG ) );
        assertEquals( 2, classResults.getViolationCount( IsisMetrics.CC ) );
        assertEquals( 0, classResults.getViolationCount( IsisMetrics.ML ) );
    }
    @Test
    public void violationCountML1Method(){
        int value = 16;
        classResults.setMLResult( methodAST1, value );
        assertEquals( 0, classResults.getViolationCount( IsisMetrics.KG ) );
        assertEquals( 0, classResults.getViolationCount( IsisMetrics.CC ) );
        assertEquals( 1, classResults.getViolationCount( IsisMetrics.ML ) );
    }
    @Test
    public void violationCountML2Methods(){
        int value = 16;
        classResults.setMLResult( methodAST1, value );
        classResults.setMLResult( methodAST2, value );
        assertEquals( 0, classResults.getViolationCount( IsisMetrics.KG ) );
        assertEquals( 0, classResults.getViolationCount( IsisMetrics.CC ) );
        assertEquals( 2, classResults.getViolationCount( IsisMetrics.ML ) );
    }
    @Test
    public void violationNamesNoMethods(){
        List<String> nameList = new ArrayList<String>();
        classResults.getViolationNames( IsisMetrics.KG, nameList );
        assertEquals(0, nameList.size());
        classResults.getViolationNames( IsisMetrics.CC, nameList );
        assertEquals(0, nameList.size());
        classResults.getViolationNames( IsisMetrics.ML, nameList );
        assertEquals(0, nameList.size());
    }
    
    @Test
    public void violationNamesCC1Method(){
        int value = 6;
        classResults.setCCResult( methodAST1, value );
        List<String> nameList = new ArrayList<String>();
        classResults.getViolationNames( IsisMetrics.KG, nameList );
        assertEquals(0, nameList.size());
        classResults.getViolationNames( IsisMetrics.ML, nameList );
        assertEquals(0, nameList.size());
        classResults.getViolationNames( IsisMetrics.CC, nameList );
        assertEquals(1, nameList.size());
        assertEquals( METHODNAME1, nameList.get( 0 ) );
    }
    @Test
    public void violationNamesCC2Methods(){
        int value = 6;
        classResults.setCCResult( methodAST1, value );
        classResults.setCCResult( methodAST2, value );
        List<String> nameList = new ArrayList<String>();
        classResults.getViolationNames( IsisMetrics.KG, nameList );
        assertEquals(0, nameList.size());
        classResults.getViolationNames( IsisMetrics.ML, nameList );
        assertEquals(0, nameList.size());
        classResults.getViolationNames( IsisMetrics.CC, nameList );
        assertEquals(2, nameList.size());
        assertTrue(nameList.contains( METHODNAME1 ));
        assertTrue(nameList.contains( METHODNAME2 ));
    }
    @Test
    public void violationNamesML1Method(){
        int value = 16;
        classResults.setMLResult( methodAST1, value );
        List<String> nameList = new ArrayList<String>();
        classResults.getViolationNames( IsisMetrics.KG, nameList );
        assertEquals(0, nameList.size());
        classResults.getViolationNames( IsisMetrics.CC, nameList );
        assertEquals(0, nameList.size());
        classResults.getViolationNames( IsisMetrics.ML, nameList );
        assertEquals(1, nameList.size());
        assertEquals( METHODNAME1, nameList.get( 0 ) );
    }

    @Test
    public void violationNamesML2Methods(){
        int value = 16;
        classResults.setMLResult( methodAST1, value );
        classResults.setMLResult( methodAST2, value );
        List<String> nameList = new ArrayList<String>();
        classResults.getViolationNames( IsisMetrics.KG, nameList );
        assertEquals(0, nameList.size());
        classResults.getViolationNames( IsisMetrics.CC, nameList );
        assertEquals(0, nameList.size());
        classResults.getViolationNames( IsisMetrics.ML, nameList );
        assertEquals(2, nameList.size());
        assertTrue(nameList.contains( METHODNAME1 ));
        assertTrue(nameList.contains( METHODNAME2 ));
    }
    @Test
    public void violationLineNosNoMethods(){
        List<Integer> linenoList = new ArrayList<Integer>();
        classResults.getViolationLineNumbers( IsisMetrics.KG, linenoList );
        assertEquals(0, linenoList.size());
        classResults.getViolationLineNumbers( IsisMetrics.CC, linenoList );
        assertEquals(0, linenoList.size());
        classResults.getViolationLineNumbers( IsisMetrics.ML, linenoList );
        assertEquals(0, linenoList.size());
    }
    
    @Test
    public void violationLineNosCC1Method(){
        int value = 6;
        classResults.setCCResult( methodAST1, value );
        List<Integer> linenoList = new ArrayList<Integer>();
        classResults.getViolationLineNumbers( IsisMetrics.KG, linenoList );
        assertEquals(0, linenoList.size());
        classResults.getViolationLineNumbers( IsisMetrics.ML, linenoList );
        assertEquals(0, linenoList.size());
        classResults.getViolationLineNumbers( IsisMetrics.CC, linenoList );
        assertEquals(1, linenoList.size());
        assertEquals( METHOD1LINENO, linenoList.get( 0 ).intValue() );
    }
    @Test
    public void violationLineNosCC2Methods(){
        int value = 6;
        classResults.setCCResult( methodAST1, value );
        classResults.setCCResult( methodAST2, value );
        List<Integer> linenoList = new ArrayList<Integer>();
        classResults.getViolationLineNumbers( IsisMetrics.KG, linenoList );
        assertEquals(0, linenoList.size());
        classResults.getViolationLineNumbers( IsisMetrics.ML, linenoList );
        assertEquals(0, linenoList.size());
        classResults.getViolationLineNumbers( IsisMetrics.CC, linenoList );
        assertEquals(2, linenoList.size());
        assertTrue(linenoList.contains( new Integer(METHOD1LINENO) ));
        assertTrue(linenoList.contains( new Integer(METHOD2LINENO) ));
    }
    @Test
    public void violationLineNosML1Method(){
        int value = 16;
        classResults.setMLResult( methodAST1, value );
        List<Integer> linenoList = new ArrayList<Integer>();
        classResults.getViolationLineNumbers( IsisMetrics.KG, linenoList );
        assertEquals(0, linenoList.size());
        classResults.getViolationLineNumbers( IsisMetrics.CC, linenoList );
        assertEquals(0, linenoList.size());
        classResults.getViolationLineNumbers( IsisMetrics.ML, linenoList );
        assertEquals(1, linenoList.size());
        assertEquals( METHOD1LINENO, linenoList.get( 0 ).intValue() );
    }
    
    @Test
    public void violationLineNosML2Methods(){
        int value = 16;
        classResults.setMLResult( methodAST1, value );
        classResults.setMLResult( methodAST2, value );
        List<Integer> nameList = new ArrayList<Integer>();
        classResults.getViolationLineNumbers( IsisMetrics.KG, nameList );
        assertEquals(0, nameList.size());
        classResults.getViolationLineNumbers( IsisMetrics.CC, nameList );
        assertEquals(0, nameList.size());
        classResults.getViolationLineNumbers( IsisMetrics.ML, nameList );
        assertEquals(2, nameList.size());
        assertTrue(nameList.contains( new Integer(METHOD1LINENO) ));
        assertTrue(nameList.contains( new Integer(METHOD2LINENO) ));
    }
    
    // TODO missing: KG tests
}
