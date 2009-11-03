package org.projectusus.core.internal.proportions.sqi;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.internal.proportions.model.IHotspot;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class ClassResultsTest {

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
        classResults = new ClassResults(  CLASSNAME, CLASSLINENO );
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
    public void addHotspotsNoMethods(){
        List<IHotspot> nameList = new ArrayList<IHotspot>();
        classResults.addHotspots( IsisMetrics.KG, nameList );
        assertEquals(0, nameList.size());
        classResults.addHotspots( IsisMetrics.CC, nameList );
        assertEquals(0, nameList.size());
        classResults.addHotspots( IsisMetrics.ML, nameList );
        assertEquals(0, nameList.size());
    }
    
//    @Test
//    public void violationNamesCC1Method(){
//        int value = 6;
//        classResults.setCCResult( methodAST1, value );
//        List<String> nameList = new ArrayList<String>();
//        classResults.getViolationNames( IsisMetrics.KG, nameList );
//        assertEquals(0, nameList.size());
//        classResults.getViolationNames( IsisMetrics.ML, nameList );
//        assertEquals(0, nameList.size());
//        classResults.getViolationNames( IsisMetrics.CC, nameList );
//        assertEquals(1, nameList.size());
//        assertEquals( METHODNAME1, nameList.get( 0 ) );
//    }
//    @Test
//    public void violationNamesCC2Methods(){
//        int value = 6;
//        classResults.setCCResult( methodAST1, value );
//        classResults.setCCResult( methodAST2, value );
//        List<String> nameList = new ArrayList<String>();
//        classResults.getViolationNames( IsisMetrics.KG, nameList );
//        assertEquals(0, nameList.size());
//        classResults.getViolationNames( IsisMetrics.ML, nameList );
//        assertEquals(0, nameList.size());
//        classResults.getViolationNames( IsisMetrics.CC, nameList );
//        assertEquals(2, nameList.size());
//        assertTrue(nameList.contains( METHODNAME1 ));
//        assertTrue(nameList.contains( METHODNAME2 ));
//    }
//    @Test
//    public void violationNamesML1Method(){
//        int value = 16;
//        classResults.setMLResult( methodAST1, value );
//        List<String> nameList = new ArrayList<String>();
//        classResults.getViolationNames( IsisMetrics.KG, nameList );
//        assertEquals(0, nameList.size());
//        classResults.getViolationNames( IsisMetrics.CC, nameList );
//        assertEquals(0, nameList.size());
//        classResults.getViolationNames( IsisMetrics.ML, nameList );
//        assertEquals(1, nameList.size());
//        assertEquals( METHODNAME1, nameList.get( 0 ) );
//    }
//
//    @Test
//    public void violationNamesML2Methods(){
//        int value = 16;
//        classResults.setMLResult( methodAST1, value );
//        classResults.setMLResult( methodAST2, value );
//        List<String> nameList = new ArrayList<String>();
//        classResults.getViolationNames( IsisMetrics.KG, nameList );
//        assertEquals(0, nameList.size());
//        classResults.getViolationNames( IsisMetrics.CC, nameList );
//        assertEquals(0, nameList.size());
//        classResults.getViolationNames( IsisMetrics.ML, nameList );
//        assertEquals(2, nameList.size());
//        assertTrue(nameList.contains( METHODNAME1 ));
//        assertTrue(nameList.contains( METHODNAME2 ));
//    }
    
    // TODO missing: KG tests
}
