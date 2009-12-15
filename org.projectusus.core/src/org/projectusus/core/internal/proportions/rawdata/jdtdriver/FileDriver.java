// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata.jdtdriver;

import static java.util.Arrays.asList;
import static org.eclipse.jdt.core.JavaCore.createCompilationUnitFrom;
import static org.eclipse.jdt.core.dom.ASTParser.newParser;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class FileDriver {

    private final IFile file;

    public FileDriver( IFile file ) {
        this.file = file;
    }

    public void compute() {
        ICompilationUnit compilationUnit = createCompilationUnitFrom( file );
        CompilationUnit parse = parse( compilationUnit );
        IJavaProject javaProject = compilationUnit.getJavaProject();
        try {
            IPackageFragmentRoot packageFragmentRoot = javaProject.getPackageFragmentRoot( compilationUnit.getUnderlyingResource() );
            // if( packageFragmentRoot.getKind() == IPackageFragmentRoot.K_SOURCE ) {

            for( ASTVisitor visitor : asList( new ClassVisitor( file ), new ML( file ), new CC( file ), new ACD( file ) ) ) {
                parse.accept( visitor );
            }
            // }
        } catch( JavaModelException jme ) {
            // ignore for the time being
        }
    }

    private CompilationUnit parse( ICompilationUnit unit ) {
        ASTParser parser = newParser( AST.JLS3 );
        parser.setKind( ASTParser.K_COMPILATION_UNIT );
        parser.setSource( unit );
        parser.setResolveBindings( true );
        return (CompilationUnit)parser.createAST( new NullProgressMonitor() );
    }
}
