package org.projectusus.core.internal.proportions.sqi.acd;

import java.util.List;

public interface ILinkedNode {
    String getName();

    int getChildCount();

    boolean isMarked();

    void setMarked( boolean state );

    List<ILinkedNode> getChildren();
}
