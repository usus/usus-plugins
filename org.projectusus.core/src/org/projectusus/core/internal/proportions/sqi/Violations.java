// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.sqi;

import java.util.HashMap;
import java.util.Map;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class Violations {

    private final Map<String, Integer> violationMap = new HashMap<String, Integer>();

    public Violations() {
        super(); // sagt AL ;)
    }

    public void resetViolationCount( DetailAST topLevelNode ) {
        put( topLevelNode, 0 );
    }

    private void put( DetailAST anAST, int value ) {
        violationMap.put( getNameOfClassDef( findEnclosingClass( anAST ) ), new Integer( value ) );
    }

    public void increaseViolationCount( DetailAST anAST ) {
        Integer currentViolations = violationMap.get( getNameOfClassDef( findEnclosingClass( anAST ) ) );
        if( currentViolations == null ) {
            put( anAST, 1 );
        } else {
            put( anAST, currentViolations.intValue() + 1 );
        }
    }

    private DetailAST findEnclosingClass( DetailAST aAST ) {
        DetailAST node = aAST;
        while( TokenTypes.CLASS_DEF != node.getType() ) {
            node = node.getParent();
        }
        return node;
    }

    private String getNameOfClassDef( DetailAST classDefNode ) {
        return classDefNode.findFirstToken( TokenTypes.IDENT ).getText();
    }

    public int getNumberOfClasses() {
        return violationMap.size();
    }

    public int getNumberOfViolations() {
        int violations = 0;
        for( Integer i : violationMap.values() ) {
            violations += i.intValue();
        }
        return violations;
    }

}
