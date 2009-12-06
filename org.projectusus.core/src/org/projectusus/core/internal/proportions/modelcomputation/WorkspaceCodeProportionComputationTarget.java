// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.modelcomputation;

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
import org.projectusus.core.internal.proportions.IFileSupport;

/**
 * convenience implementation of target that runs over the whole workspace.
 * 
 * @author leiffrenzel
 */
class WorkspaceCodeProportionComputationTarget implements ICodeProportionComputationTarget {

    public Collection<IFile> getJavaFiles( IProject project ) throws CoreException {
        return collectJavaFiles( project );
    }

    public Collection<IProject> getProjects() {
        IWorkspaceRoot wsRoot = getWorkspace().getRoot();
        return new FindUsusProjects( wsRoot.getProjects() ).compute();
    }

    public Collection<IFile> getRemovedFiles( IProject project ) throws CoreException {
        return emptyList();
    }

    // internal
    // /////////

    private Collection<IFile> collectJavaFiles( IProject project ) throws CoreException {
        Map<String, IFile> files = new HashMap<String, IFile>();
        List<IResource> resources = collectResources( project );
        for( IResource resource : resources ) {
            if( resource instanceof IFile ) {
                addJavaFile( (IFile)resource, files );
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

    private void addJavaFile( IFile file, Map<String, IFile> files ) {
        if( IFileSupport.isJavaFile( file ) ) {
            files.put( file.getLocation().toString(), file );
        }
    }

    public Collection<IProject> getRemovedProjects() {
        return new ArrayList<IProject>();
    }
}
