package org.projectusus.core.internal.proportions.rawdata;

import org.projectusus.core.internal.UsusCorePlugin;

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

    public void inspectFile( @SuppressWarnings( "unused" ) MetricsResults results ) {
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

    protected void visit() {
        UsusCorePlugin.getMetricsAccessor().acceptAndGuide( this );
    }

}
