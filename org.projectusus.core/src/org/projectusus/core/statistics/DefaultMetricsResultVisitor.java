package org.projectusus.core.statistics;

import org.eclipse.core.resources.IFile;
import org.projectusus.core.UsusModelProvider;
import org.projectusus.core.basis.CodeStatistic;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;

public abstract class DefaultMetricsResultVisitor implements MetricsResultVisitor {

    JavaModelPath path;
    final String label;

    public DefaultMetricsResultVisitor( String label ) {
        this( label, new JavaModelPath() );
    }

    public DefaultMetricsResultVisitor( String label, JavaModelPath modelPath ) {
        this.label = label;
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

    public CodeStatistic numberOfPackages() {
        return new PackageCountVisitor().visit().getCodeStatistic();
    }

    public CodeStatistic numberOfClasses() {
        return new ClassCountVisitor().visit().getCodeStatistic();
    }

    public CodeStatistic numberOfMethods() {
        return new MethodCountVisitor().visit().getCodeStatistic();
    }

    public String getLabel() {
        return label;
    }
}
