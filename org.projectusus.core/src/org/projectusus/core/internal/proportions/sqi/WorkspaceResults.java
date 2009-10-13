// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.sqi;

import java.util.ArrayList;
import java.util.List;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class WorkspaceResults extends ResultMapWrapper<ClassResults> {

    private static final int CC_LIMIT = 5;
    private static final int ML_LIMIT = 15;
    private static final int KG_LIMIT = 20;

    private final ViolationTest ccTest = new ViolationTest() {
        @Override
        public boolean isViolatedBy( MethodResults methodResult ) {
            return methodResult.getCCResult() > CC_LIMIT;
        }

        @Override
        public boolean isMethodTest() {
            return true;
        }
    };

    private final ViolationTest mlTest = new ViolationTest() {
        @Override
        public boolean isViolatedBy( MethodResults methodResults ) {
            return methodResults.getMLResult() > ML_LIMIT;
        }

        @Override
        public boolean isMethodTest() {
            return true;
        }
    };

    private final ViolationTest kgTest = new ViolationTest() {
        @Override
        public boolean isViolatedBy( ClassResults classResult ) {
            return classResult.getResultCount() > KG_LIMIT;
        }

        @Override
        public boolean isMethodTest() {
            return false;
        }
    };

    private static final WorkspaceResults instance = new WorkspaceResults();

    private WorkspaceResults() {
        super(); // sagt AL ;)
    }

    public static WorkspaceResults getInstance() {
        return instance;
    }

    public void setCCResult( DetailAST methodAST, int value ) {
        getResults( methodAST ).setCCResult( methodAST, value );
    }

    public void setMLResult( DetailAST methodAST, int value ) {
        getResults( methodAST ).setMLResult( methodAST, value );
    }

    public int getNumberOfClasses() {
        return getResultCount();
    }

    public int getNumberOfMethods() {
        int methods = 0;
        for( ClassResults classResult : getAllResults() ) {
            methods += classResult.getResultCount();
        }
        return methods;
    }

    public int getCCViolationCount() {
        return getMethodViolationCount( ccTest );
    }

    public int getCCViolationBasis() {
        return getNumberOfMethods();
    }

    public int getMLViolationCount() {
        return getMethodViolationCount( mlTest );
    }

    public int getMLViolationBasis() {
        return getNumberOfMethods();
    }

    public int getKGViolationCount() {
        return getClassViolationCount( kgTest );
    }

    public int getKGViolationBasis() {
        return getNumberOfClasses();
    }

    public List<String> getCCViolationNames() {
        return getMethodViolationNames( ccTest );
    }

    public List<String> getMLViolationNames() {
        return getMethodViolationNames( mlTest );
    }

    public List<String> getKGViolationNames() {
        return classViolationNames( kgTest );
    }

    private int getClassViolationCount( ViolationTest test ) {
        int violations = 0;
        for( ClassResults classResult : getAllResults() ) {
            if( classResult.violates( test ) ) {
                violations++;
            }
        }
        return violations;
    }

    private int getMethodViolationCount( ViolationTest test ) {
        int violations = 0;
        for( ClassResults classResult : getAllResults() ) {
            for( MethodResults methodResult : classResult.getAllResults() ) {
                if( methodResult.violates( test ) ) {
                    violations++;
                }
            }
        }
        return violations;
    }

    private List<String> classViolationNames( ViolationTest test ) {
        List<String> violations = new ArrayList<String>();
        for( ClassResults classResult : getAllResults() ) {
            if( classResult.violates( test ) ) {
                violations.add( classResult.getFullClassName() );
            }
        }
        return violations;
    }

    private List<String> getMethodViolationNames( ViolationTest test ) {
        List<String> violations = new ArrayList<String>();
        for( ClassResults classResult : getAllResults() ) {
            for( MethodResults methodResult : classResult.getAllResults() ) {
                if( methodResult.violates( test ) ) {
                    violations.add( classResult.getFullClassName() + "." + methodResult.getMethodName() );
                }
            }
        }
        return violations;
    }

    private ClassResults getResults( DetailAST methodAST ) {
        String className = getFullNameOfClassDef( findEnclosingClass( methodAST ) );
        ClassResults classResults = getResults( className, new ClassResults( className ) );
        return classResults;
    }

    private DetailAST findEnclosingClass( DetailAST aAST ) {
        DetailAST node = aAST;
        while( node != null && TokenTypes.CLASS_DEF != node.getType() ) {
            node = node.getParent();
        }
        return node;
    }

    private String getFullNameOfClassDef( DetailAST classDefNode ) {
        if( classDefNode == null ) {
            return "";
        }
        String className = classDefNode.findFirstToken( TokenTypes.IDENT ).getText();
        String packageName = getPackageName( classDefNode );
        if( packageName != "" ) {
            packageName = packageName + ".";
        }
        return packageName + className;
    }

    private String getPackageName( DetailAST classDefNode ) {
        DetailAST packageNode = classDefNode.getPreviousSibling();
        while( packageNode != null && TokenTypes.PACKAGE_DEF != packageNode.getType() ) {
            packageNode = packageNode.getPreviousSibling();
        }

        String packageName = "";
        if( packageNode != null ) {
            DetailAST dotNode = packageNode.findFirstToken( TokenTypes.DOT );
            if( dotNode != null ) {
                packageName = FullIdent.createFullIdent( dotNode ).getText();
            } else {
                packageName = packageNode.findFirstToken( TokenTypes.IDENT ).getText();
            }
        }
        return packageName;
    }
}
