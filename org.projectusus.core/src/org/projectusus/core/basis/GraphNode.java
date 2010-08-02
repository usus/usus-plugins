package org.projectusus.core.basis;

import java.util.Collection;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.projectusus.core.filerelations.model.Packagename;

public interface GraphNode {

    Set<? extends GraphNode> getChildren();

    String getNodeName();

    String getEdgeStartLabel();

    String getEdgeMiddleLabel();

    String getEdgeEndLabel();

    int getFilterValue();

    boolean isVisibleFor( int limit );

    boolean isPackage();

    IFile getFile();

    public abstract boolean isPackageOneOf( Collection<Packagename> packages );

    boolean isAtEitherEndOf( Packagename source, Packagename dest );
}
