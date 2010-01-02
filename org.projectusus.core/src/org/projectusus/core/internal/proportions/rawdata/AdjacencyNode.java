// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AdjacencyNode {

    private final Set<ClassRawData> directChildren = new HashSet<ClassRawData>();
    private final Set<ClassRawData> directParents = new HashSet<ClassRawData>();
    private final AllKnownClasses allKnownClasses = new AllKnownClasses();
    private final ClassRawData self;

    public AdjacencyNode( ClassRawData self ) {
        this.self = self;
    }

    public void addChild( ClassRawData child ) {
        allKnownClasses.invalidate();
        directChildren.add( child );
    }

    public void addParent( ClassRawData parent ) {
        directParents.add( parent );
    }

    public int getChildCount() {
        return directChildren.size();
    }

    public Set<ClassRawData> getChildren() {
        return Collections.unmodifiableSet( directChildren );
    }

    public Set<ClassRawData> getParents() {
        return Collections.unmodifiableSet( directParents );
    }

    public Set<ClassRawData> getAllKnownClasses() {
        updateKnownClasses();
        return allKnownClasses.getClasses();
    }

    private void updateKnownClasses() {
        if( allKnownClasses.areUpToDate() ) {
            return;
        }
        allKnownClasses.startInitialization( self );
        for( ClassRawData child : directChildren ) {
            allKnownClasses.addKnownClassesOf( child );
        }
    }

    public void removeParent( ClassRawData classRawData ) {
        directParents.remove( classRawData );
    }

    public void removeChild( ClassRawData classRawData ) {
        allKnownClasses.invalidate();
        directChildren.remove( classRawData );
    }

    public int getCCD() {
        updateKnownClasses();
        return allKnownClasses.size();
    }

    public void invalidate() {
        if( !allKnownClasses.areUpToDate() ) {
            return; // already invalidated
        }
        allKnownClasses.invalidate();
        for( ClassRawData parent : directParents ) {
            parent.invalidateAcd();
        }
    }

    public void removeNode( ClassRawData classRawData ) {
        invalidate();
        for( ClassRawData parent : getParents() ) {
            parent.removeChild( classRawData );
        }
        for( ClassRawData child : getChildren() ) {
            child.removeParent( classRawData );
        }
    }

}
