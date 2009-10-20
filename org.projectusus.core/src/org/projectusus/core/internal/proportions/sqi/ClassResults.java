package org.projectusus.core.internal.proportions.sqi;

import java.util.List;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class ClassResults extends Results<String, MethodResults> {

    private final String fullClassName;

    public ClassResults( String className ) {
        fullClassName = className;
    }

    public void setCCResult( DetailAST methodAST, int value ) {
        getResults( methodAST ).setCCResult( value );
    }

    public void setMLResult( DetailAST methodAST, int value ) {
        getResults( methodAST ).setMLResult( value );
    }

    private MethodResults getResults( DetailAST methodAST ) {
        String methodName = getNameOfMethod( findEnclosingMethod( methodAST ) );
        MethodResults methodResults = getResults( methodName, new MethodResults( getFullClassName(), methodName ) );
        return methodResults;
    }

    private String getNameOfMethod( DetailAST methodAST ) {
        if( methodAST == null ) {
            return ""; //$NON-NLS-1$
        }

        return methodAST.findFirstToken( TokenTypes.IDENT ).getText();
    }

    private DetailAST findEnclosingMethod( DetailAST anAST ) {
        DetailAST methodAST = anAST;
        while( methodAST != null && TokenTypes.METHOD_DEF != methodAST.getType() ) {
            methodAST = methodAST.getParent();
        }
        return methodAST;
    }

    public String getFullClassName() {
        return fullClassName;
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
            violations.add( this.getFullClassName() );
        }
    }

}
