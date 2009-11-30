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
