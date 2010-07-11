package org.projectusus.core.statistics;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.basis.CodeStatistic;
import org.projectusus.core.basis.IHotspot;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;

public interface MetricsResultVisitor {

    void inspectProject( MetricsResults results );

    void inspectFile( IFile file, MetricsResults results );

    void inspectClass( SourceCodeLocation location, MetricsResults results );

    void inspectMethod( SourceCodeLocation location, MetricsResults results );

    JavaModelPath getPath();

    CodeStatistic getBasis();

    String getLabel();

    List<IHotspot> getHotspots();

    CodeProportion getCodeProportion();

    int getViolations();

    int getMetricsSum();

    void visit();
}
