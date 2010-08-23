package org.projectusus.autotestsuite.launch;

import static org.eclipse.jdt.internal.junit.launcher.TestKindRegistry.JUNIT3_TEST_KIND_ID;
import static org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jdt.core.IJavaModel;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.junit.JUnitMessages;
import org.eclipse.jdt.internal.junit.Messages;
import org.eclipse.jdt.internal.junit.launcher.ITestKind;
import org.eclipse.jdt.internal.junit.launcher.JUnitLaunchConfigurationConstants;
import org.eclipse.jdt.internal.junit.launcher.TestKindRegistry;
import org.eclipse.jdt.junit.launcher.JUnitLaunchConfigurationDelegate;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;

@SuppressWarnings( "restriction" )
public class ExtendedJUnitLaunchConfigurationDelegate extends JUnitLaunchConfigurationDelegate {

    @Override
    protected IMember[] evaluateTests( ILaunchConfiguration config, IProgressMonitor monitor ) throws CoreException {
        if( shouldRunSingleTest( config ) ) {
            return super.evaluateTests( config, monitor );
        }
        List<IJavaProject> projects = collectProjects( config );
        Set<IMember> result = collectTests( config, monitor, projects );
        checkResult( config, result );
        return result.toArray( new IMember[result.size()] );
    }

    protected boolean shouldRunSingleTest( ILaunchConfiguration config ) throws CoreException {
        return config.getAttribute( ATTR_MAIN_TYPE_NAME, "" ).length() > 0;
    }

    protected List<IJavaProject> collectProjects( ILaunchConfiguration configuration ) throws CoreException, JavaModelException {
        IJavaProject project = getJavaProject( configuration );
        List<IJavaProject> projects = new LinkedList<IJavaProject>( findRequiredProjects( project ) );
        projects.add( 0, project );
        return projects;
    }

    protected Set<IMember> collectTests( ILaunchConfiguration configuration, IProgressMonitor monitor, List<IJavaProject> projects ) throws CoreException {
        Set<IMember> result = new HashSet<IMember>();
        ITestKind testKind = findTestKind( configuration );
        monitor.beginTask( "Looking for tests", projects.size() );
        for( IJavaProject project : projects ) {
            monitor.subTask( project.getElementName() );
            testKind.getFinder().findTestsInContainer( project, result, new SubProgressMonitor( monitor, 1 ) );
        }
        monitor.done();
        return result;
    }

    protected Set<IJavaProject> findRequiredProjects( IJavaProject project ) throws JavaModelException {
        Set<IJavaProject> projects = new LinkedHashSet<IJavaProject>();
        IJavaModel model = project.getJavaModel();
        for( String name : project.getRequiredProjectNames() ) {
            IJavaProject required = model.getJavaProject( name );
            projects.add( required );
            // this works since there cannot be cycles in project dependencies
            projects.addAll( findRequiredProjects( required ) );
        }
        return projects;
    }

    private ITestKind findTestKind( ILaunchConfiguration configuration ) {
        ITestKind testKind = JUnitLaunchConfigurationConstants.getTestRunnerKind( configuration );
        if( testKind.isNull() ) {
            testKind = TestKindRegistry.getDefault().getKind( JUNIT3_TEST_KIND_ID ); // backward compatible for launch configurations with no runner
        }
        return testKind;
    }

    private void checkResult( ILaunchConfiguration config, Collection<IMember> result ) throws CoreException {
        if( result.isEmpty() ) {
            abort( Messages.format( JUnitMessages.JUnitLaunchConfigurationDelegate_error_notests_kind, findTestKind( config ).getDisplayName() ), null,
                    IJavaLaunchConfigurationConstants.ERR_UNSPECIFIED_MAIN_TYPE );
        }
    }
}
