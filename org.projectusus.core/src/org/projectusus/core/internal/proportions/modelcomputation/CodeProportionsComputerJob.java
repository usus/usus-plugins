// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.modelcomputation;

import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.projectusus.core.internal.util.CoreTexts.codeProportionsComputerJob_name;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.projectusus.core.internal.UsusCorePlugin;
import org.projectusus.core.internal.proportions.IUsusModelWriteAccess;
import org.projectusus.core.internal.proportions.rawdata.jdtdriver.JDTDriver;

public class CodeProportionsComputerJob extends Job {

    public static final Object FAMILY = new Object();
    private final ICodeProportionComputationTarget target;

    public CodeProportionsComputerJob( ICodeProportionComputationTarget target ) {
        super( codeProportionsComputerJob_name );
        setRule( getWorkspace().getRoot() );
        setPriority( Job.DECORATE );
        this.target = target;
    }

    @Override
    public boolean belongsTo( Object family ) {
        return family == FAMILY;
    }

    @Override
    protected IStatus run( IProgressMonitor mo ) {
        IProgressMonitor monitor = mo == null ? new NullProgressMonitor() : mo;
        return performRun( monitor );
    }

    private IStatus performRun( IProgressMonitor monitor ) {
        IStatus result = Status.OK_STATUS;
        try {
            computeJavaCodeMetrics( monitor );
        } catch( CoreException cex ) {
            result = cex.getStatus();
        } finally {
            updateModel( result );
        }
        return result;
    }

    private void updateModel( IStatus result ) {
        UsusCorePlugin.getUsusModelWriteAccess().updateAfterComputationRun( result.isOK() );
    }

    private void computeJavaCodeMetrics( IProgressMonitor monitor ) throws CoreException {
        new JDTDriver( target ).run( monitor );
    }
}
