// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.checkstyledriver;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.eclipsecs.core.builder.CheckerFactory;
import net.sf.eclipsecs.core.config.ICheckConfiguration;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.JavaCore;

import com.puppycrawl.tools.checkstyle.Checker;

class ProjectAuditor {

    private final CheckResultsCollector collector;
    private final ICheckConfiguration checkConfiguration;
    private final Map<String, IFile> mFiles = new HashMap<String, IFile>();

    ProjectAuditor( ICheckConfiguration checkConfiguration, CheckResultsCollector collector ) {
        this.checkConfiguration = checkConfiguration;
        this.collector = collector;
    }

    void runAudit( IProject project, IProgressMonitor monitor ) throws Exception {
        collectFilesToAudit( project );
        if( !mFiles.isEmpty() ) {
            ProjectAuditListener listener = new ProjectAuditListener( collector.getProjectResult( project ) );
            Checker checker = null;
            try {
                checker = CheckerFactory.createChecker( checkConfiguration, project );
                checker.addListener( listener );
                reconfigureSharedClassLoader( project );
                checker.process( getFilesToAudit() );
                if( listener.hasErrors() ) {
                    throw new CoreException( listener.getErrors() );
                }
            } finally {
                if( checker != null ) {
                    checker.removeListener( listener );
                }
            }
        }
    }

    private void collectFilesToAudit( IProject project ) throws CoreException {
        List<IResource> resources = collectResources( project );
        for( IResource resource : resources ) {
            if( resource instanceof IFile ) {
                addFile( (IFile)resource );
            }
        }
    }

    private void reconfigureSharedClassLoader( IProject project ) throws CoreException {
        if( project.hasNature( JavaCore.NATURE_ID ) ) {
            CheckerFactory.getSharedClassLoader().intializeWithProject( project );
        }
    }

    private void addFile( IFile file ) {
        mFiles.put( file.getLocation().toString(), file );
    }

    private List<File> getFilesToAudit() {
        List<File> files = new ArrayList<File>();
        for( IFile file : mFiles.values() ) {
            files.add( file.getLocation().toFile() );
        }
        return files;
    }

    private List<IResource> collectResources( final IContainer container ) throws CoreException {
        List<IResource> resources = new ArrayList<IResource>();
        IResource[] children = container.members();
        for( IResource element : children ) {
            IResource child = element;
            resources.add( child );
            if( child instanceof IContainer ) {
                resources.addAll( collectResources( (IContainer)child ) );
            }
        }
        return resources;
    }

}
