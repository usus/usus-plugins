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
        DetailAST firstToken = ast.findFirstToken( type );
        if( firstToken == null ) {
            return null;
        }
        return new BetterDetailAST( firstToken );
    }

    public BetterDetailAST getParent() {
        DetailAST parent = ast.getParent();
        if( parent == null ) {
            return null;
        }
        return new BetterDetailAST( parent );
    }

    public BetterDetailAST getPreviousSibling() {
        DetailAST previousSibling = ast.getPreviousSibling();
        if( previousSibling == null ) {
            return null;
        }
        return new BetterDetailAST( previousSibling );
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ast == null) ? 0 : ast.hashCode());
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if( this == obj ) {
            return true;
        }
        if( obj == null ) {
            return false;
        }
        if( getClass() != obj.getClass() ) {
            return false;
        }
        BetterDetailAST other = (BetterDetailAST)obj;
        if( ast == null ) {
            if( other.ast != null ) {
                return false;
            }
        } else if( !ast.equals( other.ast ) ) {
            return false;
        }
        return true;
    }

}
