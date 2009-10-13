// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

import static org.projectusus.core.internal.proportions.IsisMetrics.CW;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.projectusus.core.internal.proportions.checkstyledriver.CheckstyleDriver;
import org.projectusus.core.internal.proportions.sqi.WorkspaceResults;
import org.projectusus.core.internal.yellowcount.IYellowCountResult;
import org.projectusus.core.internal.yellowcount.YellowCount;

class CodeProportionsComputerJob extends Job {

    CodeProportionsComputerJob() {
        super( "Code proportions computer" );
    }

    @Override
    protected IStatus run( IProgressMonitor mo ) {
        IProgressMonitor monitor = mo == null ? new NullProgressMonitor() : mo;
        monitor.beginTask( "Computing code proportions (ISIS metrics)", 1024 );
        return performRun( monitor );
    }

    private IStatus performRun( IProgressMonitor monitor ) {
        IStatus result = Status.OK_STATUS;
        try {
            performComputation( monitor );
            getModel().updateLastComputerRun();
        } catch( CoreException cex ) {
            getModel().updateLastComputerRun( false );
            result = cex.getStatus();
        } finally {
            monitor.done();
        }
        return result;
    }

    private void performComputation( IProgressMonitor monitor ) throws CoreException {
        computeCW( new SubProgressMonitor( monitor, 128 ) );
        computeCheckstyleBasedMetrics( new SubProgressMonitor( monitor, 512 ) );
    }

    private void computeCheckstyleBasedMetrics( IProgressMonitor monitor ) throws CoreException {
        CheckstyleDriver driver = new CheckstyleDriver();
        driver.buildAll( monitor );

        WorkspaceResults results = WorkspaceResults.getInstance();

        IsisMetrics metric = IsisMetrics.CC;
        String ccLabel = results.getCCViolationCount() + " violations in " + results.getCCViolationBasis() + " methods: ";
        for( String ccViolation : results.getCCViolationNames() ) {
            ccLabel += ccViolation + " ";
        }
        getModel().add( new CodeProportion( metric, ccLabel, results.getCCViolationCount() ) );

        String mlLabel = results.getMLViolationCount() + " violations in " + results.getMLViolationBasis() + " methods: ";
        for( String mlViolation : results.getMLViolationNames() ) {
            mlLabel += mlViolation + " ";
        }
        getModel().add( new CodeProportion( IsisMetrics.ML, mlLabel, results.getMLViolationCount() ) );

        String kgLabel = results.getKGViolationCount() + " violations in " + results.getKGViolationBasis() + " classes: ";
        for( String kgViolation : results.getKGViolationNames() ) {
            kgLabel += kgViolation + " ";
        }
        getModel().add( new CodeProportion( IsisMetrics.KG, kgLabel, results.getKGViolationCount() ) );

        // XXX hier ansetzen!
        // ProjectCheckResult cumulatedResult = driver.getCumulatedResult();
        // for( IsisMetrics metric : asList( CC, KG, ML ) ) {
        // IIsisMetricsCheckResult result = cumulatedResult.get( metric );
        // String label = result.getNumberOfViolations() + " violations in " + result.getNumberOfCases() + " files.";
        // getModel().add( new CodeProportion( metric, label, result.getNumberOfViolations() ) );
        // }
    }

    private void computeCW( IProgressMonitor monitor ) {
        IYellowCountResult yellowCountResult = YellowCount.getInstance().count();
        String cwAsString = String.valueOf( yellowCountResult.getYellowCount() );
        getModel().add( new CodeProportion( CW, cwAsString, yellowCountResult.getYellowCount() ) );
    }

    private UsusModel getModel() {
        return (UsusModel)UsusModel.getInstance();
    }
}
