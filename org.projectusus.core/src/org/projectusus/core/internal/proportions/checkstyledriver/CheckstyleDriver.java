// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.checkstyledriver;

import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.eclipse.core.runtime.IStatus.ERROR;
import static org.projectusus.core.internal.UsusCorePlugin.getPluginId;

import java.util.List;

import net.sf.eclipsecs.core.config.CheckConfigurationFactory;
import net.sf.eclipsecs.core.config.ICheckConfiguration;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.projectusus.core.internal.project.FindUsusProjects;

public class CheckstyleDriver {

    private static final String ISIS_CHECK_CONFIG = "ISIS"; //$NON-NLS-1$

    private final CheckResultsCollector collector = new CheckResultsCollector();

    public void buildAll( IProgressMonitor monitor ) throws CoreException {
        for( IProject project : getProjects() ) {
            runCheckConfig( project, monitor );
        }
    }

    public ProjectCheckResult getCumulatedResult() {
        return collector.cumulate();
    }

    private final void runCheckConfig( IProject project, IProgressMonitor monitor ) throws CoreException {
        try {
            ProjectAuditor auditor = new ProjectAuditor( findISISConfig(), collector );
            auditor.runAudit( project, monitor );
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

    private List<IProject> getProjects() {
        IWorkspaceRoot wsRoot = getWorkspace().getRoot();
        return new FindUsusProjects( wsRoot.getProjects() ).compute();
    }
}
