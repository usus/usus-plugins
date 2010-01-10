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
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.projectusus.core.internal.proportions.rawdata.collectors.ACDCollector;
import org.projectusus.core.internal.proportions.rawdata.collectors.CCCollector;
import org.projectusus.core.internal.proportions.rawdata.collectors.ClassCollector;
import org.projectusus.core.internal.proportions.rawdata.collectors.MLCollector;

public class FileDriver {

    private final IFile file;

    public FileDriver( IFile file ) {
        this.file = file;
    }

    public void compute() {
        ICompilationUnit compilationUnit = createCompilationUnitFrom( file );
        CompilationUnit parse = parse( compilationUnit );
        for( ASTVisitor visitor : asList( new ClassCollector( file ), new MLCollector( file ), new CCCollector( file ), new ACDCollector( file ) ) ) {
            parse.accept( visitor );
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
