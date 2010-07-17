package org.projectusus.core.statistics;

import org.eclipse.core.resources.IFile;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;

public interface IMetricsResultVisitor {

    void inspectProject( MetricsResults results );

    void inspectFile( IFile file, MetricsResults results );

    void inspectClass( SourceCodeLocation location, MetricsResults results );

    void inspectMethod( SourceCodeLocation location, MetricsResults results );

    JavaModelPath getPath();
}
