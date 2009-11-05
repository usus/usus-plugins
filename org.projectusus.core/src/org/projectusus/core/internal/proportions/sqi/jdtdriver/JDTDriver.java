// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.sqi.jdtdriver;

import static org.eclipse.core.runtime.IStatus.ERROR;
import static org.projectusus.core.internal.UsusCorePlugin.getPluginId;
import static org.projectusus.core.internal.util.TracingOption.SQI;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.projectusus.core.internal.proportions.ICodeProportionComputationTarget;
import org.projectusus.core.internal.proportions.sqi.NewWorkspaceResults;

public class JDTDriver {

    private final ICodeProportionComputationTarget target;

    public JDTDriver( ICodeProportionComputationTarget target ) {
        this.target = target;
    }

    public void run( IProgressMonitor monitor ) throws CoreException {
        for( IProject removedProject : target.getRemovedProjects() ) {
            NewWorkspaceResults.getInstance().dropResults( removedProject );
        }
        for( IProject project : target.getProjects() ) {
            for( IFile removedFile : target.getRemovedFiles( project ) ) {
                NewWorkspaceResults.getInstance().dropResults( removedFile );
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
        Set<IStatus> errors = new HashSet<IStatus>();
        computationStarted( project );
        for( IFile file : files ) {
            try {
                fileStarted( file );
                new FileDriver( file ).compute();
                fileFinished();
            } catch( Exception ex ) {
                addException( errors, ex );
            }
        }
        computationFinished();
        if( !errors.isEmpty() ) {
            throw new CoreException( createMultiStatusFrom( errors ) );
        }
    }

    private void addException( Set<IStatus> errors, Throwable thr ) {
        if( thr instanceof CoreException ) {
            errors.add( ((CoreException)thr).getStatus() );
        } else {
            addNewStatusFrom( errors, thr );
        }
    }

    private void addNewStatusFrom( Set<IStatus> errors, Throwable thr ) {
        String msg = thr.getMessage() == null ? "[No details.]" : thr.getMessage(); //$NON-NLS-1$
        errors.add( new Status( IStatus.ERROR, getPluginId(), msg, thr ) );
    }

    private IStatus createMultiStatusFrom( Set<IStatus> collectedErrors ) {
        String message = "Errors occurred during ISIS computation."; //$NON-NLS-1$
        MultiStatus result = new MultiStatus( getPluginId(), ERROR, message, null );
        for( IStatus error : collectedErrors ) {
            result.add( error );
        }
        return result;
    }

    private void computationStarted( IProject project ) {
        SQI.trace( "Computation started: " + project.toString() ); //$NON-NLS-1$
        NewWorkspaceResults.getInstance().setCurrentProject( project );
    }

    private void fileStarted( IFile file ) {
        SQI.trace( "File started: " + file.getFullPath() ); //$NON-NLS-1$
        NewWorkspaceResults.getInstance().dropResults( file );
        NewWorkspaceResults.getInstance().setCurrentFile( file );
    }

    private void fileFinished() {
        NewWorkspaceResults.getInstance().setCurrentFile( null );
    }

    private void computationFinished() {
        NewWorkspaceResults.getInstance().setCurrentProject( null );
    }
}
