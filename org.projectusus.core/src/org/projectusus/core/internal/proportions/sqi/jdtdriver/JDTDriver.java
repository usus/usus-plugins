// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.sqi.jdtdriver;

import static org.projectusus.core.internal.util.TracingOption.SQI;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.projectusus.core.internal.proportions.ICodeProportionComputationTarget;
import org.projectusus.core.internal.proportions.sqi.WorkspaceResults;

public class JDTDriver {

    private final ICodeProportionComputationTarget target;

    public JDTDriver( ICodeProportionComputationTarget target ) {
        this.target = target;
    }

    public void run( IProgressMonitor monitor ) throws CoreException {
        for( IProject removedProject : target.getRemovedProjects() ) {
            WorkspaceResults.getInstance().dropResults( removedProject );
        }
        for( IProject project : target.getProjects() ) {
            for( IFile removedFile : target.getRemovedFiles( project ) ) {
                WorkspaceResults.getInstance().dropResults( removedFile );
            }
            runInternal( project, monitor );
        }
    }

    private void runInternal( IProject project, IProgressMonitor monitor ) throws CoreException {
        Collection<IFile> files = target.getJavaFiles( project );
        if( !files.isEmpty() ) {
            runDriver( project, files );
        }
    }

    private void runDriver( IProject project, Collection<IFile> files ) throws CoreException {
        StatusCollector statusCollector = new StatusCollector();
        runDriver( project, files, statusCollector );
        statusCollector.finish();
    }

    private void runDriver( IProject project, Collection<IFile> files, StatusCollector statusCollector ) {
        computationStarted( project );
        for( IFile file : files ) {
            fileStarted( file );
            runDriverOnFile( file, statusCollector );
            fileFinished();
        }
        computationFinished();
    }

    private void runDriverOnFile( IFile file, StatusCollector statusCollector ) {
        try {
            new FileDriver( file ).compute();
        } catch( Exception ex ) {
            statusCollector.add( ex );
        }
    }

    private void computationStarted( IProject project ) {
        SQI.trace( "Computation started: " + project.toString() ); //$NON-NLS-1$
        WorkspaceResults.getInstance().setCurrentProject( project );
    }

    private void fileStarted( IFile file ) {
        SQI.trace( "File started: " + file.getFullPath() ); //$NON-NLS-1$
        WorkspaceResults.getInstance().dropResults( file );
        WorkspaceResults.getInstance().setCurrentFile( file );
    }

    private void fileFinished() {
        WorkspaceResults.getInstance().setCurrentFile( null );
    }

    private void computationFinished() {
        WorkspaceResults.getInstance().setCurrentProject( null );
    }
}
