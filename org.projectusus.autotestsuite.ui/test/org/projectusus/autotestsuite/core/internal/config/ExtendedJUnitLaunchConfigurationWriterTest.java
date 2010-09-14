package org.projectusus.autotestsuite.core.internal.config;

import static java.util.Arrays.asList;
import static org.eclipse.jdt.internal.junit.launcher.JUnitLaunchConfigurationConstants.ATTR_KEEPRUNNING;
import static org.eclipse.jdt.internal.junit.launcher.JUnitLaunchConfigurationConstants.ATTR_TEST_CONTAINER;
import static org.eclipse.jdt.internal.junit.launcher.JUnitLaunchConfigurationConstants.ATTR_TEST_METHOD_NAME;
import static org.eclipse.jdt.internal.junit.launcher.JUnitLaunchConfigurationConstants.ATTR_TEST_RUNNER_KIND;
import static org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME;
import static org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.projectusus.autotestsuite.core.internal.config.ExtendedJUnitLaunchConfigurationConstants.ATTR_CHECKED_PROJECTS;
import static org.projectusus.autotestsuite.core.internal.config.ExtendedJUnitLaunchConfigurationConstants.DEFAULT_TEST_KIND_ID;

import java.util.Collections;

import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.internal.junit.launcher.ITestKind;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.projectusus.autotestsuite.core.internal.config.ExtendedJUnitLaunchConfigurationWriter;

@SuppressWarnings( "restriction" )
public class ExtendedJUnitLaunchConfigurationWriterTest {

    @Rule
    public Mockery mockery = new Mockery().with( new TestKindIdInitializer() ).with( new JavaProjectNamer() );

    @Mock
    public ILaunchConfigurationWorkingCopy config;

    private ExtendedJUnitLaunchConfigurationWriter writer;

    @Before
    public void createWriter() {
        writer = new ExtendedJUnitLaunchConfigurationWriter( config );
    }

    @Test
    public void setProjectName() {
        writer.setProjectName( "Usus" );

        verify( config ).setAttribute( ATTR_PROJECT_NAME, "Usus" );
        verifyNoMoreInteractions( config );
    }

    @Test
    public void setTestContainer() {
        writer.setTestContainer( "unused" );

        verify( config ).setAttribute( ATTR_TEST_CONTAINER, "unused" );
        verifyNoMoreInteractions( config );
    }

    @Test
    public void setUnusedAttributesToDefaults() {
        writer.setUnusedAttributesToDefaults();

        verify( config ).setAttribute( ATTR_MAIN_TYPE_NAME, "" );
        verify( config ).setAttribute( ATTR_TEST_METHOD_NAME, "" );
        verifyNoMoreInteractions( config );
    }

    @Test
    public void setKeepRunning() throws Exception {
        writer.setKeepRunning( true );

        verify( config ).setAttribute( ATTR_KEEPRUNNING, true );
        verifyNoMoreInteractions( config );
    }

    @Test
    public void setTestKind() throws Exception {
        writer.setTestKind( mockery.mock( ITestKind.class, "123" ) );

        verify( config ).setAttribute( ATTR_TEST_RUNNER_KIND, "123" );
        verifyNoMoreInteractions( config );
    }

    @Test
    public void setDefaultTestKind() {
        writer.setDefaultTestKind();

        verify( config ).setAttribute( ATTR_TEST_RUNNER_KIND, DEFAULT_TEST_KIND_ID );
        verifyNoMoreInteractions( config );
    }

    @Test
    public void renameTo() {
        writer.renameTo( "foo" );

        verify( config ).rename( "foo" );
        verifyNoMoreInteractions( config );
    }

    @Test
    public void setCheckedProjects() {
        writer.setCheckedProjects( asList( mockery.mock( IJavaProject.class, "a" ), mockery.mock( IJavaProject.class, "b" ) ) );

        verify( config ).setAttribute( ATTR_CHECKED_PROJECTS, "a/b" );
        verifyNoMoreInteractions( config );
    }

    @Test
    public void emptyValueWithoutAnyCheckedProjects() {
        writer.setCheckedProjects( Collections.<IJavaProject> emptyList() );

        verify( config ).setAttribute( ATTR_CHECKED_PROJECTS, "" );
        verifyNoMoreInteractions( config );
    }
}
