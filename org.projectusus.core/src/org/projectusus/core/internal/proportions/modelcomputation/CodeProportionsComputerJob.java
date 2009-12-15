// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.modelcomputation;

import static java.util.Arrays.asList;
import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.ACD;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.CC;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.KG;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.ML;
import static org.projectusus.core.internal.util.CoreTexts.codeProportionsComputerJob_name;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.projectusus.core.internal.UsusCorePlugin;
import org.projectusus.core.internal.project.FindUsusProjects;
import org.projectusus.core.internal.proportions.IUsusModelWriteAccess;
import org.projectusus.core.internal.proportions.model.CodeProportion;
import org.projectusus.core.internal.proportions.modelupdate.ComputationRunModelUpdate;
import org.projectusus.core.internal.proportions.rawdata.CodeProportionKind;
import org.projectusus.core.internal.proportions.rawdata.WorkspaceRawData;
import org.projectusus.core.internal.proportions.rawdata.jdtdriver.JDTDriver;
import org.projectusus.core.internal.proportions.yellowcount.WorkspaceYellowCount;

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
        List<CodeProportion> collector = new ArrayList<CodeProportion>();
        try {
            performComputation( collector, monitor );
        } catch( CoreException cex ) {
            result = cex.getStatus();
        } finally {
            updateModel( result, collector );
        }
        return result;
    }

    private void updateModel( IStatus result, List<CodeProportion> collector ) {
        UsusCorePlugin ususCorePlugin = UsusCorePlugin.getDefault();
        // we are inside a background job, and the plugin might have been
        // shut down meanwhile
        if( ususCorePlugin != null ) {
            IUsusModelWriteAccess ususModel = ususCorePlugin.getUsusModelWriteAccess();
            ususModel.update( new ComputationRunModelUpdate( collector, result.isOK() ) );
        }
    }

    private void performComputation( List<CodeProportion> collector, IProgressMonitor monitor ) throws CoreException {
        collector.add( new WorkspaceYellowCount( getUsusProjects() ).getCodeProportion() );
        computeJavaCodeMetrics( collector, monitor );
    }

    private List<IProject> getUsusProjects() {
        return new FindUsusProjects( getWSRoot().getProjects() ).compute();
    }

    private IWorkspaceRoot getWSRoot() {
        return getWorkspace().getRoot();
    }

    private void computeJavaCodeMetrics( List<CodeProportion> collector, IProgressMonitor monitor ) throws CoreException {
        new JDTDriver( target ).run( monitor );

        for( CodeProportionKind metric : asList( CC, KG, ML, ACD ) ) {
            collector.add( WorkspaceRawData.getInstance().getCodeProportion( metric ) );
        }
    }
}
