// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import java.util.ArrayList;
import java.util.List;

public class AdjacencyNode {

    private final List<ClassRawData> children;
    private final List<ClassRawData> parents;
    private boolean marked;

    public AdjacencyNode() {
        children = new ArrayList<ClassRawData>();
        parents = new ArrayList<ClassRawData>();
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

    public List<ClassRawData> getChildren() {
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

    public boolean containsChild( ClassRawData referencedRawData ) {
        return children.contains( referencedRawData );
    }

    public boolean containsParent( ClassRawData classRawData ) {
        return parents.contains( classRawData );
    }

    // Knoten durch Einfuegen in Set markieren, damit Zaehlen + Loeschen in O(1) ??
    public void markReferencedNodes() {
        if( this.isMarked() ) {
            return;
        }
        this.mark();
        for( ClassRawData childNode : this.getChildren() ) {
            childNode.markReferencedNodes();
        }
    }
}
