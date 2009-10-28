// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.sqi;

import java.util.List;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class ClassResults extends Results<String, MethodResults> {

    private final String packageName;
    private final String className;
    private final int lineNo;

    public ClassResults( String packageName, String className, int lineNo ) {
        this.packageName = packageName;
        this.className = className;
        this.lineNo = lineNo;
    }

    public void setCCResult( BetterDetailAST methodAST, int value ) {
        getResults( methodAST ).setCCResult( value );
    }

    public void setMLResult( BetterDetailAST methodAST, int value ) {
        getResults( methodAST ).setMLResult( value );
    }

    private MethodResults getResults( BetterDetailAST resultAST ) {
        BetterDetailAST methodAST = findEnclosingMethod( resultAST );
        String methodName = getNameOfMethod( methodAST );
        MethodResults methodResults = getResults( methodName, new MethodResults( getPackageName(), getClassName(), methodName, methodAST.getLineNo() ) );
        return methodResults;
    }

    private String getNameOfMethod( BetterDetailAST methodAST ) {
        if( methodAST == null ) {
            return ""; //$NON-NLS-1$
        }

        return methodAST.findFirstToken( TokenTypes.IDENT ).getText();
    }

    private BetterDetailAST findEnclosingMethod( BetterDetailAST anAST ) {
        BetterDetailAST methodAST = anAST;
        while( methodAST != null && TokenTypes.METHOD_DEF != methodAST.getType() ) {
            methodAST = methodAST.getParent();
        }
        return methodAST;
    }

    public String getClassName() {
        return className;
    }

    public String getPackageName() {
        return packageName;
    }

    public int getLineNo() {
        return lineNo;
    }

    @Override
    public int getViolationBasis( IsisMetrics metric ) {
        if( metric.isMethodTest() ) {
            return super.getViolationBasis( metric );
        }
        return 1;
    }

    @Override
    public int getViolationCount( IsisMetrics metric ) {
        if( metric.isMethodTest() ) {
            return super.getViolationCount( metric );
        }
        return metric.isViolatedBy( this ) ? 1 : 0;
    }

    @Override
    public void getViolationNames( IsisMetrics metric, List<String> violations ) {
        if( metric.isMethodTest() ) {
            super.getViolationNames( metric, violations );
        } else if( metric.isViolatedBy( this ) ) {
            violations.add( this.getClassName() );
        }
    }

    @Override
    public void getViolationLineNumbers( IsisMetrics metric, List<Integer> violations ) {
        if( metric.isMethodTest() ) {
            super.getViolationLineNumbers( metric, violations );
        } else if( metric.isViolatedBy( this ) ) {
            violations.add( new Integer( this.getLineNo() ) );
        }

    }

}
