// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.sqi;

import org.projectusus.core.internal.proportions.IsisMetrics;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;


public class KG extends SQICheck {

    private static final int METHODEN_LIMIT = 10;

    @Override
    public int[] getDefaultTokens() {
        return new int[] { TokenTypes.CLASS_DEF };
    }

    @Override
    public void visitToken( DetailAST ast ) {
        DetailAST objBlock = ast.findFirstToken( TokenTypes.OBJBLOCK );
        int methodenDefs = objBlock.getChildCount( TokenTypes.METHOD_DEF );
        analyzeMethodCount( ast, methodenDefs );
    }

    private void analyzeMethodCount( DetailAST ast, int methodenDefs ) {
        if( methodenDefs > METHODEN_LIMIT ) {
            increaseViolations( ast );
        }
    }

    @Override
    protected IsisMetrics getMetrics() {
        return IsisMetrics.KG;
    }
}
