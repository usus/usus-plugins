package org.projectusus.core.internal.proportions.sqi;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

public class ProjectResults extends ResultMapWrapper<IFile, FileResults> {

    private final IProject projectOfResults;
    private IFile currentFile;

    public ProjectResults( IProject project ) {
        this.projectOfResults = project;
    }

    public void setCurrentFile( IFile currentFile ) {
        this.currentFile = currentFile;
    }

    public FileResults getCurrentFileResults() {
        return getResults( currentFile, new FileResults( currentFile ) );
    }

    public void dropResults( IFile file ) {
        remove( file );
    }

    // FIXME: refactor, join with other classes
    public int getViolationCount( IsisMetrics metric ) {
        int violations = 0;
        for( FileResults fileResult : getAllResults() ) {
            violations = violations + fileResult.getViolationCount( metric );
        }
        return violations;
    }

    public int getViolationBasis( IsisMetrics metric ) {
        int basis = 0;
        for( FileResults fileResult : getAllResults() ) {
            basis = basis + fileResult.getViolationBasis( metric );
        }
        return basis;
    }

    public void getViolationNames( IsisMetrics metric, List<String> violations ) {
        for( FileResults fileResult : getAllResults() ) {
            fileResult.getViolationNames( metric, violations );
        }
    }

}
