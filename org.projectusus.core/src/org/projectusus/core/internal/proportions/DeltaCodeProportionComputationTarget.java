// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

import static java.util.Collections.unmodifiableCollection;
import static org.eclipse.core.resources.IResourceDelta.ADDED;
import static org.eclipse.core.resources.IResourceDelta.CHANGED;
import static org.eclipse.core.resources.IResourceDelta.REMOVED;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;

class DeltaCodeProportionComputationTarget implements ICodeProportionComputationTarget {

    private final Map<IProject, List<IFile>> changes = new HashMap<IProject, List<IFile>>();
    private final Map<IProject, List<IFile>> deletions = new HashMap<IProject, List<IFile>>();

    DeltaCodeProportionComputationTarget( IResourceDelta delta ) throws CoreException {
        compute( delta );
    }

    public Collection<IFile> getFiles( IProject project ) throws CoreException {
        return unmodifiableCollection( changes.get( project ) );
    }

    public Collection<IProject> getProjects() {
        return unmodifiableCollection( changes.keySet() );
    }

    public Collection<IFile> getRemovedFiles( IProject project ) throws CoreException {
        return null;
    }

    // internal
    // /////////

    private void compute( IResourceDelta delta ) throws CoreException {
        delta.accept( new ChangedCollector() );
    }

    private final class ChangedCollector implements IResourceDeltaVisitor {

        public boolean visit( IResourceDelta delta ) throws CoreException {
            IResource resource = delta.getResource();
            if( resource instanceof IFile ) {
                if( isInteresting( delta.getKind() ) ) {
                    addToMap( (IFile)resource, changes );
                } else if( isDeleted( delta.getKind() ) ) {
                    addToMap( (IFile)resource, deletions );
                }

            }
            return true; // always visit the children, until the bitter end on the ground
        }

        private void addToMap( IFile file, Map<IProject, List<IFile>> collector ) {
            IProject project = file.getProject();
            if( !collector.containsKey( project ) ) {
                collector.put( project, new ArrayList<IFile>() );
            }
            collector.get( project ).add( file );
        }

        private boolean isDeleted( int kind ) {
            return (kind & REMOVED) != 0;
        }

        private boolean isInteresting( int kind ) {
            return (kind & ADDED) != 0 || (kind & CHANGED) != 0;
        }
    }

    public Collection<IProject> getRemovedProjects() {
        // TODO lf ergänzen
        return new ArrayList<IProject>();
    }
}
