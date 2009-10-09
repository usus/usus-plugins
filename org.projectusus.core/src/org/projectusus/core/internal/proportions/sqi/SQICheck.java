// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.sqi;

import org.projectusus.core.internal.proportions.IsisMetrics;
import org.projectusus.core.internal.proportions.checkstyledriver.IsisMetricsCheckResult;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;


public abstract class SQICheck extends Check {

    private Violations violations;

    @Override
    public void init() {
        super.init();
        violations = new Violations();
    }

    @Override
    public void beginTree( DetailAST aRootAST ) {
        super.beginTree( aRootAST );
        resetViolationsInTree( aRootAST );
    }

    private void resetViolationsInTree( DetailAST aRootAST ) {
        DetailAST currentNode = aRootAST;
        while( currentNode != null ) {
            if( TokenTypes.CLASS_DEF == currentNode.getType() ) {
                violations.resetViolationCount( currentNode );
            }
            currentNode = currentNode.getNextSibling();
        }
    }

    @Override
    public void finishTree( DetailAST aRootAST ) {
        IsisMetricsCheckResult result = new IsisMetricsCheckResult( getMetrics(), violations );
        log( aRootAST, result.toString() );
        super.finishTree( aRootAST );
    }

    protected abstract IsisMetrics getMetrics();

    protected void increaseViolations( DetailAST aAST ) {
        violations.increaseViolationCount( aAST );
    }

}
