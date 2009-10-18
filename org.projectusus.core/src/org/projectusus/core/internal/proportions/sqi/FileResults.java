// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.sqi;

import java.util.List;

import org.eclipse.core.resources.IFile;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class FileResults extends ResultMapWrapper<String, ClassResults> {

    private final IFile fileOfResults;

    public FileResults( IFile file ) {
        super(); // sagt AL ;)
        this.fileOfResults = file;
    }

    public void setCCResult( DetailAST methodAST, int value ) {
        getResults( methodAST ).setCCResult( methodAST, value );
    }

    public void setMLResult( DetailAST methodAST, int value ) {
        getResults( methodAST ).setMLResult( methodAST, value );
    }

    public void addClass( DetailAST classAST ) {
        getResults( classAST );
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

    public int getViolationBasis( IsisMetrics metric ) {
        if( metric.isMethodTest() ) {
            return getNumberOfMethods();
        }
        return getNumberOfClasses();
    }

    public int getViolationCount( IsisMetrics metric ) {
        int violations = 0;
        for( ClassResults classResult : getAllResults() ) {
            violations = violations + classResult.getViolations( metric );
        }
        return violations;
    }

    public void getViolationNames( IsisMetrics metric, List<String> violations ) {
        for( ClassResults classResult : getAllResults() ) {
            classResult.addViolatingTargets( metric, violations );
        }
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
            return ""; //$NON-NLS-1$
        }
        String className = classDefNode.findFirstToken( TokenTypes.IDENT ).getText();
        String packageName = getPackageName( classDefNode );
        if( packageName != "" ) { //$NON-NLS-1$
            packageName = packageName + "."; //$NON-NLS-1$
        }
        return packageName + className;
    }

    private String getPackageName( DetailAST classDefNode ) {
        DetailAST packageNode = classDefNode.getPreviousSibling();
        while( packageNode != null && TokenTypes.PACKAGE_DEF != packageNode.getType() ) {
            packageNode = packageNode.getPreviousSibling();
        }

        String packageName = ""; //$NON-NLS-1$
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
