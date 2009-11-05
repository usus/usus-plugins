// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

import static org.eclipse.core.resources.IResourceDelta.ADDED;
import static org.eclipse.core.resources.IResourceDelta.CHANGED;
import static org.eclipse.core.resources.IResourceDelta.REMOVED;
import static org.projectusus.core.internal.util.TracingOption.RESOURCE_CHANGES;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.projectusus.core.internal.project.IsUsusProject;

class ChangedResourcesCollector implements IResourceDeltaVisitor {

    private final List<IProject> removedProjects;
    private final Map<IProject, List<IFile>> changes;
    private final Map<IProject, List<IFile>> deletions;

    ChangedResourcesCollector( List<IProject> removedProjects, Map<IProject, List<IFile>> changes, Map<IProject, List<IFile>> deletions ) {
        this.changes = changes;
        this.removedProjects = removedProjects;
        this.deletions = deletions;
    }

    public boolean visit( IResourceDelta delta ) throws CoreException {
        boolean result = true;
        IResource resource = delta.getResource();
        if( handleRemovedProject( delta ) || isNonUsusProject( resource ) ) {
            RESOURCE_CHANGES.trace( "Removed " + resource.getFullPath() ); //$NON-NLS-1$
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
        if( isInteresting( delta.getKind() ) ) {
            RESOURCE_CHANGES.trace( "Changed file " + file.getFullPath() ); //$NON-NLS-1$
            addToMap( file, changes );
        } else if( isDeleted( delta.getKind() ) ) {
            RESOURCE_CHANGES.trace( "Deleted file " + file.getFullPath() ); //$NON-NLS-1$
            addToMap( file, deletions );
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
