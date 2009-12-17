// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import java.util.HashSet;
import java.util.Set;

public class AdjacencyNode {

    private final Set<ClassRawData> children;
    private final Set<ClassRawData> parents;
    private boolean marked;

    public AdjacencyNode() {
        children = new HashSet<ClassRawData>();
        parents = new HashSet<ClassRawData>();
        marked = false;
    }

    public void addChild( ClassRawData child ) {
        children.add( child );
    }

    public void addParent( ClassRawData parent ) {
        parents.add( parent );
    }

    public int getChildCount() {
        return children.size();
    }

    public Set<ClassRawData> getChildren() {
        return children;
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

    // Knoten durch Einfuegen in Set markieren, damit Zaehlen + Loeschen in O(1) ??
    public void markReferencedNodes() {
        if( isMarked() ) {
            return;
        }
        mark();
        for( ClassRawData childNode : getChildren() ) {
            childNode.markReferencedNodes();
        }
    }
}
