package org.projectusus.core.internal.proportions;

import static java.util.Collections.unmodifiableCollection;
import static org.eclipse.core.resources.IResourceDelta.ADDED;
import static org.eclipse.core.resources.IResourceDelta.CHANGED;

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

    private final Map<IProject, List<IFile>> content = new HashMap<IProject, List<IFile>>();

    DeltaCodeProportionComputationTarget( IResourceDelta delta ) throws CoreException {
        compute( delta );
    }

    public Collection<IFile> getFiles( IProject project ) throws CoreException {
        return unmodifiableCollection( content.get( project ) );
    }

    public Collection<IProject> getProjects() {
        return unmodifiableCollection( content.keySet() );
    }

    // internal
    // /////////

    private void compute( IResourceDelta delta ) throws CoreException {
        delta.accept( new ChangedCollector( content ) );
    }

    private static final class ChangedCollector implements IResourceDeltaVisitor {

        private final Map<IProject, List<IFile>> collector;

        private ChangedCollector( Map<IProject, List<IFile>> collector ) {
            this.collector = collector;
        }

        public boolean visit( IResourceDelta delta ) throws CoreException {
            IResource resource = delta.getResource();
            if( resource instanceof IFile ) {
                IFile file = (IFile)resource;
                if( isInteresting( delta.getKind() ) ) {
                    IProject project = file.getProject();
                    if( !collector.containsKey( project ) ) {
                        collector.put( project, new ArrayList<IFile>() );
                    }
                    collector.get( project ).add( file );
                }
            }
            return true; // always visit the children, until the bitter end on the ground
        }

        private boolean isInteresting( int kind ) {
            // TODO lf what about removed files? We should tell our check about that
            return (kind & ADDED) != 0 || (kind & CHANGED) != 0;
        }
    }

}
