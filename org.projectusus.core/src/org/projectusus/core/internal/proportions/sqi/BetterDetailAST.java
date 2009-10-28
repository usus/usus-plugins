package org.projectusus.core.internal.proportions.sqi;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;

// no final in here!!!
public class BetterDetailAST {

    private final DetailAST ast;

    public BetterDetailAST( DetailAST ast ) {
        this.ast = ast;
    }

    public BetterDetailAST findFirstToken( int type ) {
        return new BetterDetailAST( ast.findFirstToken( type ) );
    }

    public BetterDetailAST getParent() {
        return new BetterDetailAST( ast.getParent() );
    }

    public BetterDetailAST getPreviousSibling() {
        return new BetterDetailAST( ast.getPreviousSibling() );
    }

    public int getType() {
        return ast.getType();
    }

    public String getText() {
        return ast.getText();
    }

    public String getFullIdentText() {
        return FullIdent.createFullIdent( ast ).getText();
    }

    public int getLineNo() {
        return ast.getLineNo();
    }

}
