// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.checkstyledriver;

import static org.eclipse.core.runtime.IStatus.ERROR;
import static org.projectusus.core.internal.UsusCorePlugin.getPluginId;
import static org.projectusus.core.internal.proportions.checkstyledriver.IsisMetricsCheckResult.fromString;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;

class ProjectAuditListener implements AuditListener {
    private final ProjectCheckResult projectResult;
    private final Set<IStatus> errors = new HashSet<IStatus>();

    ProjectAuditListener( ProjectCheckResult projectResult ) {
        this.projectResult = projectResult;
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
        projectResult.update( fromString( event.getMessage() ) );
    }

    public void fileStarted( AuditEvent event ) {
        // unused
    }

    public void fileFinished( AuditEvent event ) {
        // unused
    }

    public void auditStarted( AuditEvent event ) {
        // unused
    }

    public void auditFinished( AuditEvent event ) {
        // unused
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

}
