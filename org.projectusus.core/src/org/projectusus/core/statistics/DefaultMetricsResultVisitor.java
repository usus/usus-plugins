package org.projectusus.core.statistics;

import org.eclipse.core.resources.IFile;
import org.projectusus.core.UsusModelProvider;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;

/**
 * Default implementation of <code>IMetricsResultVisitor</code>.
 * <p>
 * Implementors of raw data visitors can use this implementation as a basis for their own implementations. It provides empty inspection methods that can be overwritten if desired.
 * <p>
 * The visitors can visit the whole raw data tree or only a subtree. To identify the subtree to be visited, the visitor specifies the root node of the subtree by passing a
 * <code>JavaModelPath</code> object in the constructor.
 * 
 * @author Nicole Rauch
 * 
 */
public abstract class DefaultMetricsResultVisitor implements IMetricsResultVisitor {

    JavaModelPath path;

    public DefaultMetricsResultVisitor() {
        this( new JavaModelPath() );
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

    public void visit() {
        UsusModelProvider.getMetricsAccessor().acceptAndGuide( this );
    }
}
