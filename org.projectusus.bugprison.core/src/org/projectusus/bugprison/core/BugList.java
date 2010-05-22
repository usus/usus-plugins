// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.bugprison.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BugList implements Serializable, Iterable<Bug> {

    private static final long serialVersionUID = -7139843196318353350L;
    private final List<Bug> bugs = new ArrayList<Bug>();

    public void addBug( Bug bug ) {
        bugs.add( bug );
    }

    public Iterator<Bug> iterator() {
        return bugs.iterator();
    }

    public void addBugs( BugList other ) {
        bugs.addAll( other.bugs );
    }

    public Bug[] getBugs() {
        return bugs.toArray( new Bug[bugs.size()] );
    }

    public AverageBugMetrics getAverageMetrics() {
        AverageBugMetrics result = new AverageBugMetrics();
        for( Bug bug : bugs ) {
            result.addBugMetrics( bug.getBugMetrics() );
        }
        return result;
    }

    public void addBugs( List<Bug> other ) {
        bugs.addAll( other );
    }

    public BugList filter( MethodLocation methodLocation ) {
        BugList result = new BugList();
        for( Bug bug : this ) {
            if( bug.getLocation().equals( methodLocation ) ) {
                result.addBug( bug );
            }
        }
        return result;
    }

    public int size() {
        return bugs.size();
    }

    public boolean isEmpty() {
        return bugs.isEmpty();
    }

    public void setProjectName( String projectName ) {
        for( Bug bug : this ) {
            bug.getLocation().setProject( projectName );
        }
    }
}
