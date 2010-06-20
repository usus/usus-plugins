package org.projectusus.core.internal.proportions.rawdata;

public interface MetricsResultVisitor {

    void inspectProject( MetricsResults results );

    void inspectFile( MetricsResults results );

    void inspectClass( SourceCodeLocation location, MetricsResults results );

    void inspectMethod( SourceCodeLocation location, MetricsResults results );

    JavaModelPath getPath();
}
