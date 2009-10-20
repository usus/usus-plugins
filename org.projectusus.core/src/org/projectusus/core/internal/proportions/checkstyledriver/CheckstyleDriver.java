// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.checkstyledriver;

import static org.eclipse.core.runtime.IStatus.ERROR;
import static org.projectusus.core.internal.UsusCorePlugin.getPluginId;
import net.sf.eclipsecs.core.config.CheckConfigurationFactory;
import net.sf.eclipsecs.core.config.ICheckConfiguration;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.projectusus.core.internal.proportions.ICodeProportionComputationTarget;
import org.projectusus.core.internal.proportions.sqi.NewWorkspaceResults;

public class CheckstyleDriver {

    private static final String ISIS_CHECK_CONFIG = "ISIS"; //$NON-NLS-1$

    private final ICodeProportionComputationTarget target;

    public CheckstyleDriver( ICodeProportionComputationTarget target ) {
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
            runCheckConfig( project, monitor );
        }
    }

    private final void runCheckConfig( IProject project, IProgressMonitor monitor ) throws CoreException {
        try {
            ProjectAuditor auditor = new ProjectAuditor( findISISConfig() );
            auditor.runAudit( project, target.getFiles( project ), monitor );
        } catch( CoreException cex ) {
            throw cex;
        } catch( Exception ex ) {
            fireCoreException( ex );
        }
    }

    private void fireCoreException( Exception ex ) throws CoreException {
        String msg = ex.getMessage() == null ? "[No details]" : ex.getMessage(); //$NON-NLS-1$
        throw new CoreException( new Status( ERROR, getPluginId(), msg, ex ) );
    }

    private ICheckConfiguration findISISConfig() {
        return CheckConfigurationFactory.getByName( ISIS_CHECK_CONFIG );
    }
}
