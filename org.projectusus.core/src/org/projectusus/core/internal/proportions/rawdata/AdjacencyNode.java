// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import java.util.ArrayList;
import java.util.List;

public class AdjacencyNode {

    private final String name;
    private final List<AdjacencyNode> children;
    private boolean marked;

    public AdjacencyNode( String name ) {
        this.name = name;
        children = new ArrayList<AdjacencyNode>();
        marked = false;
    }

    public int getChildCount() {
        return children.size();
    }

    public List<AdjacencyNode> getChildren() {
        return children;
    }

    public String getName() {
        return name;
    }

    public boolean isMarked() {
        return marked;
    }

    public void mark() {
        marked = true;
    }

    public int getCountAndClear() {
        int value = marked ? 1 : 0;
        marked = false;
        return value;
    }

}
