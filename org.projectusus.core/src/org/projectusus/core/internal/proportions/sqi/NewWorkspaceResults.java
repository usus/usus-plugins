package org.projectusus.core.internal.proportions.sqi;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.projectusus.core.internal.proportions.CodeProportion;

public class NewWorkspaceResults extends Results<IProject, ProjectResults> {

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

    public CodeProportion getCodeProportion( IsisMetrics metric ) {
        return new CodeProportion( metric, this.getViolationCount( metric ), this.getViolationBasis( metric ) );
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

}
