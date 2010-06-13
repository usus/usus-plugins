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

    public void inspect( @SuppressWarnings( "unused" ) ProjectRawData projectRawData ) {
        // do nothing with the data
    }

    public void inspect( @SuppressWarnings( "unused" ) FileRawData fileRawData ) {
        // do nothing with the data
    }

    public void inspect( @SuppressWarnings( "unused" ) ClassRawData classRawData ) {
        // do nothing with the data
    }

    public void inspect( @SuppressWarnings( "unused" ) MethodRawData methodRawData ) {
        // do nothing with the data
    }

    public JavaModelPath getPath() {
        return path;
    }

    protected void visit() {
        UsusCorePlugin.getMetricsAccessor().acceptAndGuide( this );
    }

}
