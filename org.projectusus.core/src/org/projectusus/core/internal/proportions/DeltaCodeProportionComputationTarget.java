// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

import static java.util.Collections.unmodifiableList;
import static org.eclipse.core.resources.IResourceDelta.ADDED;
import static org.eclipse.core.resources.IResourceDelta.CHANGED;
import static org.eclipse.core.resources.IResourceDelta.REMOVED;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.projectusus.core.internal.project.IsUsusProject;

public class DeltaCodeProportionComputationTarget implements ICodeProportionComputationTarget {

    private final Map<IProject, List<IFile>> changes = new HashMap<IProject, List<IFile>>();
    private final Map<IProject, List<IFile>> deletions = new HashMap<IProject, List<IFile>>();
    public List<IProject> removedProjects = new ArrayList<IProject>();

    public DeltaCodeProportionComputationTarget( IResourceDelta delta ) throws CoreException {
        compute( delta );
    }

    public Collection<IFile> getJavaFiles( IProject project ) throws CoreException {
        return getFilesFrom( project, changes );
    }

    public Collection<IProject> getProjects() {
        Set<IProject> result = new HashSet<IProject>();
        result.addAll( changes.keySet() );
        result.addAll( deletions.keySet() );
        return result;
    }

    public Collection<IFile> getRemovedFiles( IProject project ) throws CoreException {
        return getFilesFrom( project, deletions );
    }

    public Collection<IProject> getRemovedProjects() {
        return unmodifiableList( removedProjects );
    }

    // internal
    // /////////

    private Collection<IFile> getFilesFrom( IProject project, Map<IProject, List<IFile>> collector ) {
        List<IFile> result = new ArrayList<IFile>();
        if( collector.containsKey( project ) ) {
            result.addAll( collector.get( project ) );
        }
        return result;
    }

    private void compute( IResourceDelta delta ) throws CoreException {
        delta.accept( new ChangedCollector() );
    }

    private final class ChangedCollector implements IResourceDeltaVisitor {

        public boolean visit( IResourceDelta delta ) throws CoreException {
            boolean result = true;
            IResource resource = delta.getResource();
            if( handleRemovedProject( delta ) || isNonUsusProject( resource ) ) {
                removedProjects.add( (IProject)resource );
                result = false; // ignore the entire delta
            } else if( resource instanceof IFile ) {
                handleJavaFileDelta( delta, (IFile)resource );
            }
            return result;
        }

        private boolean isNonUsusProject( IResource resource ) {
            return resource instanceof IProject && !(new IsUsusProject( (IProject)resource ).compute());
        }

        private boolean handleRemovedProject( IResourceDelta delta ) {
            boolean result = false;
            IResource resource = delta.getResource();
            if( resource instanceof IProject ) {
                IProject project = (IProject)resource;
                result = wasClosed( delta, project ) || isDeleted( delta.getKind() );
            }
            return result;
        }

        private boolean wasClosed( IResourceDelta delta, IProject project ) {
            return isOpenCloseStatusChanged( delta ) && !project.isOpen();
        }

        private void handleJavaFileDelta( IResourceDelta delta, IFile file ) {
            if( IFileSupport.isJavaFile( file ) ) {
                if( isInteresting( delta.getKind() ) ) {
                    addToMap( file, changes );
                } else if( isDeleted( delta.getKind() ) ) {
                    addToMap( file, deletions );
                }
            }
        }

        private void addToMap( IFile file, Map<IProject, List<IFile>> collector ) {
            IProject project = file.getProject();
            if( !collector.containsKey( project ) ) {
                collector.put( project, new ArrayList<IFile>() );
            }
            collector.get( project ).add( file );
        }

        private boolean isOpenCloseStatusChanged( IResourceDelta delta ) {
            return (delta.getFlags() & IResourceDelta.OPEN) != 0;
        }

        private boolean isDeleted( int kind ) {
            return (kind & REMOVED) != 0;
        }

        private boolean isInteresting( int kind ) {
            return (kind & ADDED) != 0 || (kind & CHANGED) != 0;
        }
    }
}
