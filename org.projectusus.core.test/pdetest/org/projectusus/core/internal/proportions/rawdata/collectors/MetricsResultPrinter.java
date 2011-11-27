package org.projectusus.core.internal.proportions.rawdata.collectors;

import java.io.PrintStream;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.eclipse.core.resources.IFile;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultMetricsResultVisitor;

public class MetricsResultPrinter extends DefaultMetricsResultVisitor {

    private final PrintStream out;

    public MetricsResultPrinter( PrintStream out ) {
        this.out = out;
    }

    @Override
    public void inspectProject( MetricsResults results ) {
        out.print( "\n Project: " );
        out.println( ReflectionToStringBuilder.toString( results ) );
    }

    @Override
    public void inspectFile( IFile file, MetricsResults results ) {
        out.print( "\n File: " );
        out.println( ReflectionToStringBuilder.toString( file ) );
        out.println( ReflectionToStringBuilder.toString( results ) );
    }

    @Override
    public void inspectClass( SourceCodeLocation location, MetricsResults results ) {
        out.print( "\n Class: " );
        out.println( location.getName() );
        out.println( ReflectionToStringBuilder.toString( results ) );
    }

    @Override
    public void inspectMethod( SourceCodeLocation location, MetricsResults results ) {
        out.print( "\n Method: " );
        out.println( location.getName() );
        out.println( ReflectionToStringBuilder.toString( results ) );
    }
}