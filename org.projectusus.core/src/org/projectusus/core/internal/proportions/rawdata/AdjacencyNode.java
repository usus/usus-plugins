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
    private final SetOfClasses transitiveChildren = new SetOfClasses();
    private final Set<ClassRawData> directParents = new HashSet<ClassRawData>();
    private final SetOfClasses transitiveParents = new SetOfClasses();
    private final ClassRawData self;

    public AdjacencyNode( ClassRawData self ) {
        this.self = self;
    }

    public void addChild( ClassRawData child ) {
        transitiveChildren.invalidate();
        directChildren.add( child );
    }

    public void addParent( ClassRawData parent ) {
        transitiveParents.invalidate();
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

    public Set<ClassRawData> getAllChildren() {
        updateChildren();
        return transitiveChildren.getClasses();
    }

    public Set<ClassRawData> getAllParents() {
        updateKnowingClasses();
        return transitiveParents.getClasses();
    }

    private void updateChildren() {
        if( transitiveChildren.areUpToDate() ) {
            return;
        }
        transitiveChildren.startInitialization( self );
        for( ClassRawData child : directChildren ) {
            transitiveChildren.addAllClassesDependingOn( child, child.getAllChildren() );
        }
    }

    private void updateKnowingClasses() {
        if( transitiveParents.areUpToDate() ) {
            return;
        }
        transitiveParents.startInitialization( self );
        for( ClassRawData parent : directParents ) {
            transitiveChildren.addAllClassesDependingOn( parent, parent.getAllParents() );
        }
    }

    public void removeParent( ClassRawData classRawData ) {
        transitiveParents.invalidate();
        directParents.remove( classRawData );
    }

    public void removeChild( ClassRawData classRawData ) {
        transitiveChildren.invalidate();
        directChildren.remove( classRawData );
    }

    public int getCCD() {
        updateChildren();
        return transitiveChildren.size();
    }

    public void invalidate() { // TODO what needs to be done for the parents?
        if( !transitiveChildren.areUpToDate() ) {
            return; // already invalidated
        }
        transitiveChildren.invalidate();
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
