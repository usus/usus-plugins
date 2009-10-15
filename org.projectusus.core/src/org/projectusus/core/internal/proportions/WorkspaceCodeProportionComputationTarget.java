package org.projectusus.core.internal.proportions;

import static java.util.Collections.emptyList;
import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.projectusus.core.internal.project.FindUsusProjects;

/**
 * convenience implementation of target that runs over the whole workspace.
 * 
 * @author leiffrenzel
 */
class WorkspaceCodeProportionComputationTarget implements ICodeProportionComputationTarget {

    public Collection<IFile> getFiles( IProject project ) throws CoreException {
        return collectFiles( project );
    }

    public Collection<IProject> getProjects() {
        IWorkspaceRoot wsRoot = getWorkspace().getRoot();
        return new FindUsusProjects( wsRoot.getProjects() ).compute();
    }

    public Collection<IFile> getDeletedFiles( IProject project ) throws CoreException {
        return emptyList();
    }

    // internal
    // /////////

    private Collection<IFile> collectFiles( IProject project ) throws CoreException {
        Map<String, IFile> files = new HashMap<String, IFile>();
        List<IResource> resources = collectResources( project );
        for( IResource resource : resources ) {
            if( resource instanceof IFile ) {
                addFile( (IFile)resource, files );
            }
        }
        return files.values();
    }

    private List<IResource> collectResources( final IContainer container ) throws CoreException {
        List<IResource> resources = new ArrayList<IResource>();
        IResource[] children = container.members();
        for( IResource child : children ) {
            resources.add( child );
            if( child instanceof IContainer ) {
                resources.addAll( collectResources( (IContainer)child ) );
            }
        }
        return resources;
    }

    private void addFile( IFile file, Map<String, IFile> files ) {
        files.put( file.getLocation().toString(), file );
    }
}
