// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.proportions.rawdata.jdtdriver;

import static org.eclipse.jdt.core.JavaCore.createCompilationUnitFrom;
import static org.eclipse.jdt.core.dom.ASTParser.newParser;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageDeclaration;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.core.metrics.MetricsCollector;
import org.projectusus.core.statistics.UsusModelProvider;

public class JavaFileDriver {

    private final IFile file;

    public JavaFileDriver( IFile file ) {
        this.file = file;
    }

    public void compute( Set<MetricsCollector> metricsExtensions ) {

        ICompilationUnit compilationUnit = createCompilationUnitFrom( file );

        // Packagename packagename = getPackagename( compilationUnit );
        // if( packagename == null ) {
        // return;
        // }

        /*
         * if( packageDeclarations.length != 1 ) { return; } if( packageDeclarations[0].getElementName().contains( "internal" ) ) { return; }
         */

        CompilationUnit parse = parse( compilationUnit );

        for( MetricsCollector visitor : metricsExtensions ) {
            visitor.setup( file, UsusModelProvider.getMetricsWriter() );
            parse.accept( visitor );
        }
    }

    private static CompilationUnit parse( ICompilationUnit unit ) {
        ASTParser parser = newParser( AST.JLS3 );
        parser.setKind( ASTParser.K_COMPILATION_UNIT );
        parser.setResolveBindings( true );
        parser.setSource( unit );
        return (CompilationUnit)parser.createAST( null );
    }

    private static Packagename getPackagename( ICompilationUnit compilationUnit ) {
        IPackageDeclaration[] packageDeclarations;
        try {
            packageDeclarations = compilationUnit.getPackageDeclarations();
        } catch( JavaModelException e ) {
            return null;
        }
        if( packageDeclarations == null || packageDeclarations.length == 0 ) {
            return null;
        }
        IPackageDeclaration declaration = packageDeclarations[0];

        return Packagename.of( declaration.getElementName(), declaration );
    }

}
