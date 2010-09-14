package org.projectusus.autotestsuite.launch;

import static java.util.Arrays.asList;
import static org.projectusus.autotestsuite.launch.ExtendedJUnitLaunchConfigurationConstants.toProject;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.internal.junit.launcher.ITestKind;
import org.eclipse.jdt.junit.launcher.JUnitLaunchConfigurationDelegate;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.projectusus.autotestsuite.core.internal.AllJavaProjectsInWorkspace;
import org.projectusus.autotestsuite.core.internal.IAllJavaProjects;
import org.projectusus.autotestsuite.core.internal.RequiredJavaProjects;

public class ExtendedJUnitLaunchConfigurationDelegate extends JUnitLaunchConfigurationDelegate {

    @Override
    protected IMember[] evaluateTests( ILaunchConfiguration config, IProgressMonitor monitor ) throws CoreException {
        return evaluateTests( new ExtendedJUnitLaunchConfigurationReader( config ), monitor );
    }

    private IMember[] evaluateTests( ExtendedJUnitLaunchConfigurationReader config, IProgressMonitor monitor ) throws CoreException {
        Collection<IJavaProject> projects = collectProjects( config );
        Set<IMember> result = collectTests( config, projects, monitor );
        checkResult( config, result );
        return result.toArray( new IMember[result.size()] );
    }

    @SuppressWarnings( "restriction" )
    private Set<IMember> collectTests( ExtendedJUnitLaunchConfigurationReader config, Collection<IJavaProject> projects, IProgressMonitor monitor ) throws CoreException {
        Set<IMember> result = new HashSet<IMember>();
        ITestKind testKind = config.getTestKind();
        monitor.beginTask( "Looking for tests", projects.size() );
        for( IJavaProject project : projects ) {
            monitor.subTask( project.getElementName() );
            testKind.getFinder().findTestsInContainer( project, result, new SubProgressMonitor( monitor, 1 ) );
        }
        monitor.done();
        return result;
    }

    private Collection<IJavaProject> collectProjects( ExtendedJUnitLaunchConfigurationReader config ) {
        IJavaProject project = toProject( config.getProjectName() );
        List<IJavaProject> projects = new LinkedList<IJavaProject>( findRequired( project ) );
        IJavaProject[] checkedProjects = config.getCheckedProjects();
        projects.retainAll( asList( checkedProjects ) );
        return projects;
    }

    private Collection<IJavaProject> findRequired( IJavaProject project ) {
        IAllJavaProjects allProjects = new AllJavaProjectsInWorkspace();
        return new RequiredJavaProjects( allProjects ).findFor( project );
    }

    private void checkResult( ExtendedJUnitLaunchConfigurationReader config, Collection<IMember> result ) throws CoreException {
        if( result.isEmpty() ) {
            String pattern = "No tests found with test runner ''{0}''.";
            String message = MessageFormat.format( pattern, config.getTestKind().getDisplayName() );
            abort( message, null, IJavaLaunchConfigurationConstants.ERR_UNSPECIFIED_MAIN_TYPE );
        }
    }
}
