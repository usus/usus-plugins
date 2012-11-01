package org.projectusus.ui.internal.coveredprojects;

import static java.util.Arrays.asList;
import static org.projectusus.core.project2.UsusProjectSupport.asUsusProject;

import java.util.List;

import org.eclipse.core.resources.IProject;

public class UsusProjectMarker {

    public void check( IProject... projects ) {
        check( asList( projects ) );
    }

    public void check( Iterable<IProject> projects ) {
        mark( projects, true );
    }

    public void uncheck( IProject... projects ) {
        uncheck( asList( projects ) );
    }

    public void uncheck( List<IProject> projects ) {
        mark( projects, false );
    }

    private void mark( Iterable<IProject> projects, boolean checked ) {
        for( IProject project : projects ) {
            asUsusProject( project ).setUsusProject( checked );
        }
    }

}
