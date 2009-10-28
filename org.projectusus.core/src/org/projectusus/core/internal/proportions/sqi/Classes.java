// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.sqi;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class Classes extends Check {

    @Override
    public int[] getDefaultTokens() {
        return new int[] { TokenTypes.CLASS_DEF };
    }

    @Override
    public void visitToken( DetailAST anAST ) {
        FileResults currentFileResults = NewWorkspaceResults.getInstance().getCurrentProjectResults().getCurrentFileResults();
        currentFileResults.addClass( new BetterDetailAST( anAST ) );
    }
}
