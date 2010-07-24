package org.projectusus.core.basis;

import java.util.Set;

import org.eclipse.core.resources.IFile;

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
}
