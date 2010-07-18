package org.projectusus.core.statistics;

import org.eclipse.core.resources.IFile;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;

/**
 * Visitor interface to traverse the raw data collected by Usus.
 * <p>
 * 
 * All information about the code analyzed by Usus is stored in a tree of <code>MetricsResults</code> objects. Visitors can be used to traverse this tree and to collect information
 * at the project, file, class or method level. Those visitors must implement the <code>IMetricsResultVisitor</code> interface in order to be accepted by the UsusModel.
 * <p>
 * Visitors implementing this interface can traverse the information tree via the<br>
 * 
 * <code>UsusModelProvider.getMetricsAccessor().acceptAndGuide( aVisitor );</code><br>
 * 
 * method.
 * 
 * @author Nicole Rauch
 * 
 */
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
