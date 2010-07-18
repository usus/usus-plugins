package org.projectusus.core.statistics;

import org.eclipse.core.resources.IFile;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;

public interface IMetricsResultVisitor {

    static String codeProportionUnit_CLASS_label = "classes"; //$NON-NLS-1$
    static String codeProportionUnit_METHOD_label = "methods"; //$NON-NLS-1$
    static String codeProportionUnit_PACKAGE_label = "packages"; //$NON-NLS-1$

    void inspectProject( MetricsResults results );

    void inspectFile( IFile file, MetricsResults results );

    void inspectClass( SourceCodeLocation location, MetricsResults results );

    void inspectMethod( SourceCodeLocation location, MetricsResults results );

    JavaModelPath getPath();
}
