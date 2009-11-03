// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.checkstyledriver;

import static org.eclipse.core.runtime.IStatus.ERROR;
import static org.projectusus.core.internal.UsusCorePlugin.getPluginId;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.projectusus.core.internal.proportions.sqi.NewWorkspaceResults;
import org.projectusus.core.internal.proportions.sqi.ProjectResults;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;

class ProjectAuditListener implements AuditListener {
    private final Set<IStatus> errors = new HashSet<IStatus>();

    private final IProject currentProject;
    private final Collection<IFile> currentFiles;

    ProjectAuditListener( IProject project, Collection<IFile> files ) {
        super();
        this.currentProject = project;
        this.currentFiles = files;
    }

    boolean hasErrors() {
        return !errors.isEmpty();
    }

    IStatus getErrors() {
        return createMultiStatusFrom( errors );
    }

    // interface methods
    // //////////////////

    public void addError( AuditEvent event ) {
        // unused
    }

    public void fileStarted( AuditEvent event ) {
        for( IFile file : currentFiles ) {
            if( getFileSystemLocation( file ).equals( event.getFileName() ) ) {
                System.out.println( "fileStarted: " + event.getFileName() );
                ProjectResults currentProjectResults = NewWorkspaceResults.getInstance().getCurrentProjectResults();
                printResults( "Initial Results: ", file, currentProjectResults );
                NewWorkspaceResults.getInstance().dropResults( file );
                printResults( "Results after drop: ", file, currentProjectResults );
                NewWorkspaceResults.getInstance().setCurrentFile( file );
                printResults( "Results after set: ", file, currentProjectResults );
            }
        }
    }

    private void printResults( String comment, IFile file, ProjectResults currentProjectResults ) {
        // System.out.println( comment );
        // FileResults results = currentProjectResults.getResults( file, new FileResults( file ) );
        // System.out.println( "KG: " + results.getViolationBasis( IsisMetrics.KG ) + " " + results.getViolationCount( IsisMetrics.KG ) );
        // System.out.println( "ML: " + results.getViolationBasis( IsisMetrics.ML ) + " " + results.getViolationCount( IsisMetrics.ML ) );
        // System.out.println( "CC: " + results.getViolationBasis( IsisMetrics.CC ) + " " + results.getViolationCount( IsisMetrics.CC ) );
    }

    public void fileFinished( AuditEvent event ) {
        printResults( "Results after finished: ", NewWorkspaceResults.getInstance().getCurrentProjectResults().getCurrentFile(), NewWorkspaceResults.getInstance()
                .getCurrentProjectResults() );
        NewWorkspaceResults.getInstance().setCurrentFile( null );
    }

    public void auditStarted( AuditEvent event ) {
        System.out.println( "auditStarted: " + currentProject.toString() );
        NewWorkspaceResults.getInstance().setCurrentProject( currentProject );
    }

    public void auditFinished( AuditEvent event ) {
        NewWorkspaceResults.getInstance().setCurrentProject( null );
    }

    public void addException( AuditEvent event, Throwable thr ) {
        if( thr instanceof CoreException ) {
            errors.add( ((CoreException)thr).getStatus() );
        } else {
            addNewStatusFrom( thr );
        }
    }

    // internal methods
    // /////////////////

    private void addNewStatusFrom( Throwable thr ) {
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

    private String getFileSystemLocation( IFile file ) {
        return file.getLocation().toOSString();
    }
}
