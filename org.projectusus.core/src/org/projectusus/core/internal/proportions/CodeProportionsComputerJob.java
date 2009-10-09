// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

import static java.util.Arrays.asList;
import static org.projectusus.core.internal.proportions.IsisMetrics.CC;
import static org.projectusus.core.internal.proportions.IsisMetrics.CW;
import static org.projectusus.core.internal.proportions.IsisMetrics.KG;
import static org.projectusus.core.internal.proportions.IsisMetrics.ML;
import static org.projectusus.core.internal.proportions.IsisMetrics.TA;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.projectusus.core.internal.coverage.AggregateTestCoverageOverWorkspace;
import org.projectusus.core.internal.coverage.TestCoverage;
import org.projectusus.core.internal.proportions.checkstyledriver.CheckstyleDriver;
import org.projectusus.core.internal.proportions.checkstyledriver.IIsisMetricsCheckResult;
import org.projectusus.core.internal.proportions.checkstyledriver.ProjectCheckResult;
import org.projectusus.core.internal.yellowcount.IYellowCountResult;
import org.projectusus.core.internal.yellowcount.YellowCount;

public class CodeProportionsComputerJob extends Job {

    public CodeProportionsComputerJob() {
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
        collectTA( new SubProgressMonitor( monitor, 256 ) );
        computeCheckstyleBasedMetrics( new SubProgressMonitor( monitor, 512 ) );
    }

    private void collectTA( IProgressMonitor monitor ) {
        TestCoverage testCoverage = new AggregateTestCoverageOverWorkspace().compute();
        getModel().add( new CodeProportion( TA, testCoverage.toString() ) );
    }

    private void computeCheckstyleBasedMetrics( IProgressMonitor monitor ) throws CoreException {
        CheckstyleDriver driver = new CheckstyleDriver();
        driver.buildAll( monitor );
        ProjectCheckResult cumulatedResult = driver.getCumulatedResult();
        for( IsisMetrics metric : asList( CC, KG, ML ) ) {
            IIsisMetricsCheckResult result = cumulatedResult.get( metric );
            String label = result.getNumberOfViolations() + " violations in " + result.getNumberOfCases() + " files.";
            getModel().add( new CodeProportion( metric, label ) );
        }
    }

    private void computeCW( IProgressMonitor monitor ) {
        IYellowCountResult yellowCountResult = YellowCount.getInstance().count();
        String cwAsString = String.valueOf( yellowCountResult.getYellowCount() );
        getModel().add( new CodeProportion( CW, cwAsString ) );
    }

    private CodeProportions getModel() {
        return CodeProportions.getInstance();
    }
}
