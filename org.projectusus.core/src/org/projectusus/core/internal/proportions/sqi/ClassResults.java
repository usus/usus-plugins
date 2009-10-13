package org.projectusus.core.internal.proportions.sqi;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class ClassResults extends ResultMapWrapper<MethodResults> {

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
        MethodResults methodResults = getResults( methodName, new MethodResults( methodName ) );
        return methodResults;
    }

    private String getNameOfMethod( DetailAST methodAST ) {
        if( methodAST == null ) {
            return "";
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

    public boolean violates( ViolationTest test ) {
        return test.isViolatedBy( this );
    }

}
