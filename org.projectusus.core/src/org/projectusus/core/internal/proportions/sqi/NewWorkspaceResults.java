package org.projectusus.core.internal.proportions.sqi;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

public class NewWorkspaceResults extends ResultMapWrapper<IProject, ProjectResults> {

    private IProject currentProject;

    private static final NewWorkspaceResults instance = new NewWorkspaceResults();

    private NewWorkspaceResults() {
        super();
    }

    public static NewWorkspaceResults getInstance() {
        return instance;
    }

    public void setCurrentProject( IProject project ) {
        this.currentProject = project;
    }

    public ProjectResults getCurrentProjectResults() {
        // log warning if currentProject == null
        return getResults( currentProject, new ProjectResults( currentProject ) );
    }

    public void setCurrentFile( IFile currentFile ) {
        getCurrentProjectResults().setCurrentFile( currentFile );
    }

    public void dropResults( IProject project ) {
        remove( project );
    }

    public void dropResults( IFile file ) {
        getCurrentProjectResults().dropResults( file );
    }

    // FIXME: refactor, join with other classes
    public int getViolationCount( IsisMetrics metric ) {
        int violations = 0;
        for( ProjectResults projectResult : getAllResults() ) {
            violations = violations + projectResult.getViolationCount( metric );
        }
        return violations;
    }

    public int getViolationBasis( IsisMetrics metric ) {
        int basis = 0;
        for( ProjectResults projectResult : getAllResults() ) {
            basis = basis + projectResult.getViolationBasis( metric );
        }
        return basis;
    }

    public void getViolationNames( IsisMetrics metric, List<String> violations ) {
        for( ProjectResults projectResult : getAllResults() ) {
            projectResult.getViolationNames( metric, violations );
        }
    }

}
