package org.projectusus.core.internal.proportions.sqi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class BetterDetailASTTest {

    
    @Test
    public void createBlankBetterAST(){
        DetailAST ast = new DetailAST();
        BetterDetailAST betterAst = new BetterDetailAST( ast );
        assertNull(betterAst.findFirstToken( 0 ));
        assertNull( betterAst.getParent() );
        assertNull( betterAst.getPreviousSibling() );
        assertEquals(0, betterAst.getType());
        assertEquals(Integer.MIN_VALUE, betterAst.getLineNo() );
        assertNull( betterAst.getText() );
        assertEquals("null", betterAst.getFullIdentText() );
    }
    
    @Test
    public void createMethodBetterAST(){
        String METHODNAME = "methodname";
        DetailAST detailAST = new DetailAST();
        detailAST.setText( METHODNAME );
        detailAST.setType( TokenTypes.METHOD_DEF );
        
        BetterDetailAST betterAst = new BetterDetailAST( detailAST );
        assertNull( betterAst.findFirstToken( TokenTypes.METHOD_DEF ));
        assertNull( betterAst.getParent() );
        assertNull( betterAst.getPreviousSibling() );
        assertEquals(TokenTypes.METHOD_DEF, betterAst.getType());
        assertEquals(Integer.MIN_VALUE, betterAst.getLineNo() );
        assertEquals( METHODNAME, betterAst.getText() );
        assertEquals(METHODNAME, betterAst.getFullIdentText() );

    }
}
