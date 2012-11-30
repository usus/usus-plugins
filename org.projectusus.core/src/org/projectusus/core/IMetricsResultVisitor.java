package org.projectusus.core;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.filerelations.model.Packagename;

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

    String codeProportionUnit_CLASS_label = "classes"; //$NON-NLS-1$
    String codeProportionUnit_METHOD_label = "methods"; //$NON-NLS-1$
    String codeProportionUnit_PACKAGE_label = "packages"; //$NON-NLS-1$
    String codeProportionUnit_PACKAGE_PER_PROJECT_label = "packages/project"; //$NON-NLS-1$

    void inspectProject( IProject project, MetricsResults results );

    void inspectPackage( Packagename pkg, MetricsResults results );

    void inspectFile( IFile file, MetricsResults results );

    void inspectClass( SourceCodeLocation location, MetricsResults results );

    void inspectMethod( SourceCodeLocation location, MetricsResults results );

    JavaModelPath getPath();

    String getLabel();
}
