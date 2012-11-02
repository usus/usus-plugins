package org.projectusus.core.internal;

import static org.eclipse.core.resources.IncrementalProjectBuilder.FULL_BUILD;
import static org.eclipse.core.resources.IncrementalProjectBuilder.INCREMENTAL_BUILD;
import static org.eclipse.core.resources.ResourcesPlugin.FAMILY_AUTO_BUILD;
import static org.eclipse.core.resources.ResourcesPlugin.FAMILY_AUTO_REFRESH;
import static org.eclipse.core.resources.ResourcesPlugin.FAMILY_MANUAL_BUILD;
import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.eclipse.core.runtime.jobs.Job.getJobManager;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.rules.ExternalResource;
import org.projectusus.adapter.UsusAdapterPlugin;
import org.projectusus.core.internal.proportions.rawdata.UsusModel;

public class Workspace extends ExternalResource {

    @Override
    protected void before() throws Throwable {
        UsusAdapterPlugin.getDefault(); // to load bundle with ResourceChangeListener
    }

    @Override
    protected void after() {
        UsusModel.clear();
    }

    public void buildFullyAndWait() throws CoreException {
        buildAndWait( FULL_BUILD, "full" );
    }

    public void buildIncrementallyAndWait() throws CoreException {
        buildAndWait( INCREMENTAL_BUILD, "incremental" );
    }

    private void buildAndWait( int kind, String description ) throws CoreException {
        getWorkspace().build( kind, new NullProgressMonitor() );
        print( "  Waiting for " + description + " build to complete ..." );
        waitForBuild();
    }

    private void waitForBuild() {
        boolean retry = true;
        while( retry ) {
            try {
                getJobManager().join( FAMILY_AUTO_REFRESH, new NullProgressMonitor() );
                getJobManager().join( FAMILY_AUTO_BUILD, new NullProgressMonitor() );
                getJobManager().join( FAMILY_MANUAL_BUILD, new NullProgressMonitor() );
                retry = false;
            } catch( Exception exc ) {
                // ignore and retry
            }
        }
        println( " OK." );
    }

    private void print( String message ) {
        System.out.print( message );
        System.out.flush();
    }

    private void println( String message ) {
        System.out.println( message );
    }
}
