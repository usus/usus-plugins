package org.projectusus.ui.dependencygraph.nodes;

import java.util.Collection;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.projectusus.core.filerelations.model.Packagename;

public interface GraphNode {

    Set<? extends GraphNode> getChildren();

    Set<? extends GraphNode> getParents();

    String getDisplayText();

    String getTooltipText();

    String getImageName();

    @Deprecated
    String getEdgeStartLabel();

    @Deprecated
    String getEdgeMiddleLabel();

    String getEdgeEndLabel();

    @Deprecated
    int getFilterValue();

    IFile getFile();

    Packagename getRelatedPackage();

    boolean isPackageOneOf( Collection<Packagename> packages );

    boolean isAtEitherEndOf( Packagename source, Packagename dest );

    boolean isInDifferentPackageThan( GraphNode destination );

    boolean isVisibleForLimitWithOtherNodes( boolean restricting, Set<GraphNode> others );
}
