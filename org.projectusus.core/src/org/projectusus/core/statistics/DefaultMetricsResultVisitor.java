package org.projectusus.core.statistics;

import org.eclipse.core.resources.IFile;
import org.projectusus.core.UsusModelProvider;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;

public abstract class DefaultMetricsResultVisitor implements MetricsResultVisitor {

    private JavaModelPath path;

    public DefaultMetricsResultVisitor() {
        path = new JavaModelPath();
    }

    public DefaultMetricsResultVisitor( JavaModelPath modelPath ) {
        path = modelPath;
    }

    public void inspectProject( @SuppressWarnings( "unused" ) MetricsResults results ) {
        // do nothing with the data
    }

    public void inspectFile( @SuppressWarnings( "unused" ) IFile file, @SuppressWarnings( "unused" ) MetricsResults results ) {
        // do nothing with the data
    }

    public void inspectClass( @SuppressWarnings( "unused" ) SourceCodeLocation location, @SuppressWarnings( "unused" ) MetricsResults results ) {
        // do nothing with the data
    }

    public void inspectMethod( @SuppressWarnings( "unused" ) SourceCodeLocation location, @SuppressWarnings( "unused" ) MetricsResults results ) {
        // do nothing with the data
    }

    public JavaModelPath getPath() {
        return path;
    }

    public MetricsResultVisitor visit() {
        UsusModelProvider.getMetricsAccessor().acceptAndGuide( this );
        return this;
    }

}
