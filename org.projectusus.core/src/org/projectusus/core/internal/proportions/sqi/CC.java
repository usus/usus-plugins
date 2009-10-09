// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.sqi;

import org.projectusus.core.internal.proportions.IsisMetrics;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;


public class CC extends SQICheck {

    private int currentCountForMethod;

    @Override
    public void init() {
        super.init();
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] { TokenTypes.CTOR_DEF, TokenTypes.METHOD_DEF, TokenTypes.INSTANCE_INIT, TokenTypes.STATIC_INIT, TokenTypes.LITERAL_WHILE, TokenTypes.LITERAL_DO,
                TokenTypes.LITERAL_FOR, TokenTypes.LITERAL_IF, TokenTypes.LITERAL_CASE, TokenTypes.LITERAL_CATCH, TokenTypes.QUESTION, TokenTypes.LAND, TokenTypes.LOR, };
    }

    @Override
    public void visitToken( DetailAST aAST ) {
        switch( aAST.getType() ) {
        case TokenTypes.CTOR_DEF:
        case TokenTypes.METHOD_DEF:
        case TokenTypes.INSTANCE_INIT:
        case TokenTypes.STATIC_INIT:
            currentCountForMethod = 0;
            break;
        default:
            currentCountForMethod++;
        }
    }

    @Override
    public void leaveToken( DetailAST aAST ) {
        switch( aAST.getType() ) {
        case TokenTypes.CTOR_DEF:
        case TokenTypes.METHOD_DEF:
        case TokenTypes.INSTANCE_INIT:
        case TokenTypes.STATIC_INIT:
            analyzeCyclomaticComplexity( aAST );
        }
    }

    private void analyzeCyclomaticComplexity( DetailAST anAST ) {
        if( currentCountForMethod > 5 ) {
            increaseViolations( anAST );
        }
    }

    @Override
    protected IsisMetrics getMetrics() {
        return IsisMetrics.CC;
    }

}
