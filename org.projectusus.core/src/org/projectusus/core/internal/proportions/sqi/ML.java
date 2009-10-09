// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.sqi;

import org.projectusus.core.internal.proportions.IsisMetrics;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;


public class ML extends SQICheck {

    @Override
    protected IsisMetrics getMetrics() {
        return IsisMetrics.ML;
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] { TokenTypes.METHOD_DEF, TokenTypes.CTOR_DEF };
    }

    @Override
    public void visitToken( DetailAST anAST ) {
        DetailAST openingBrace = anAST.findFirstToken( TokenTypes.SLIST );
        if( openingBrace != null ) {
            DetailAST closingBrace = openingBrace.findFirstToken( TokenTypes.RCURLY );
            int length = calcMethodLength( openingBrace.getLineNo(), closingBrace.getLineNo() );
            analyzeMethodLength( anAST, length );
        }
    }

    private void analyzeMethodLength( DetailAST anAST, int length ) {
        if( length > 15 ) {
            increaseViolations( anAST );
        }
    }

    public int calcMethodLength( int startingLineNo, int endingLineNo ) {
        BetterFileContents contents = getBetterFileContents();
        int length = 0;
        for( int i = startingLineNo - 1; i < endingLineNo; i++ ) {
            if( !contents.lineIsBlank( i ) && !contents.lineIsComment( i ) ) {
                length++;
            }
        }
        return length;
    }

    // no final in code!!
    public BetterFileContents getBetterFileContents() {
        return new BetterFileContents( getFileContents() );
    }
}
