package org.projectusus.projectsettings.ui.internal;

import org.eclipse.core.resources.IProject;
import org.projectusus.projectsettings.core.WhichPrefs;

public class ProjectSelectorResult {
    private final IProject project;
    private final WhichPrefs[] whichPrefs;

    public ProjectSelectorResult( IProject project, WhichPrefs[] whichPrefs ) {
        super();
        this.project = project;
        this.whichPrefs = whichPrefs;
    }

    public IProject getProject() {
        return project;
    }

    public WhichPrefs[] getWhichPrefs() {
        return whichPrefs;
    }
}
