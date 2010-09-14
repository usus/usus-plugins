package org.projectusus.autotestsuite.core.internal.config;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.eclipse.jdt.internal.junit.launcher.JUnitLaunchConfigurationConstants.ATTR_KEEPRUNNING;
import static org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.projectusus.autotestsuite.core.internal.config.ExtendedJUnitLaunchConfigurationConstants.ATTR_CHECKED_PROJECTS;


import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jdt.core.IJavaProject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.projectusus.autotestsuite.core.internal.config.ExtendedJUnitLaunchConfigurationReader;

import com.google.common.base.Function;

@SuppressWarnings( "restriction" )
public class ExtendedJUnitLaunchConfigurationReaderTest {

    private static final Status DUMMY_STATUS = new Status( 0, "xx", "xx" );

    @Rule
    public Mockery mockery = new Mockery().with( new JavaProjectNamer() );

    @Mock
    private ILaunchConfiguration config;

    private ExtendedJUnitLaunchConfigurationReader reader;

    @Before
    public void createReader() {
        reader = new ExtendedJUnitLaunchConfigurationReader( config );
    }

    private final Function<String, IJavaProject> converter = new Function<String, IJavaProject>() {
        public IJavaProject apply( String name ) {
            return mockery.mock( IJavaProject.class, name );
        }
    };

    @Test
    public void parseCheckedProjects() throws Exception {
        set( ATTR_CHECKED_PROJECTS, "xxx/y/zz" );
        assertEquals( asList( "xxx", "y", "zz" ), JavaProjectNamer.collectNames( reader.getCheckedProjects( converter ) ) );
    }

    @Test
    public void noCheckedProjectsForEmptyValue() throws Exception {
        set( ATTR_CHECKED_PROJECTS, "" );
        assertEquals( emptyList(), JavaProjectNamer.collectNames( reader.getCheckedProjects( converter ) ) );
    }

    @Test
    public void noCheckedProjectsInCaseOfError() throws Exception {
        throwCoreExceptionOn( config.getAttribute( ATTR_CHECKED_PROJECTS, "" ) );
        assertEquals( emptyList(), JavaProjectNamer.collectNames( reader.getCheckedProjects( converter ) ) );
    }

    @Test
    public void projectName() throws Exception {
        set( ATTR_PROJECT_NAME, "Usus" );
        assertThat( reader.getProjectName(), is( "Usus" ) );
    }

    @Test
    public void emptyProjectNameInCaseOfError() throws Exception {
        throwCoreExceptionOn( config.getAttribute( ATTR_PROJECT_NAME, "" ) );
        assertThat( reader.getProjectName(), is( "" ) );
    }

    @Test
    public void keepRunning() throws Exception {
        set( ATTR_KEEPRUNNING, true );
        assertTrue( reader.isKeepRunning() );
    }

    @Test
    public void noKeepRunningInCaseOfError() throws Exception {
        throwCoreExceptionOn( config.getAttribute( ATTR_KEEPRUNNING, false ) );
        assertFalse( reader.isKeepRunning() );
    }

    private <T> void throwCoreExceptionOn( T call ) {
        when( call ).thenThrow( new CoreException( DUMMY_STATUS ) );
    }

    private void set( String key, String value ) throws CoreException {
        when( config.getAttribute( key, "" ) ).thenReturn( value );
    }

    private void set( String key, boolean value ) throws CoreException {
        when( config.getAttribute( key, false ) ).thenReturn( value );
    }
}
