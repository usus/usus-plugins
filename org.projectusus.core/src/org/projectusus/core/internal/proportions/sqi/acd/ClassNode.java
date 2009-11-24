package org.projectusus.core.internal.proportions.sqi.acd;

import java.util.ArrayList;
import java.util.List;

public class ClassNode {

    private final String name;
    private final List<ClassNode> children;
    private boolean marked;

    public ClassNode( String name ) {
        this.name = name;
        children = new ArrayList<ClassNode>();
        marked = false;
    }

    public int getChildCount() {
        return children.size();
    }

    public List<ClassNode> getChildren() {
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
