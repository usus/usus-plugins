package org.projectusus.core.internal.project;

import org.projectusus.core.internal.bugreport.Bug;
import org.projectusus.core.internal.bugreport.BugList;
import org.projectusus.core.internal.proportions.sqi.ProjectResults;

public class NullUsusProject implements IUSUSProject {

    public BugList getBugs() {
        return new BugList();
    }

    public boolean isUsusProject() {
        return false;
    }

    public void saveBug( Bug bug ) {
        // does not save anything
    }

    public void setUsusProject( boolean ususProject ) {
        // can't make this project an usus project
    }

    public ProjectResults getProjectResults() {
        return null;
    }

    public String getProjectName() {
        return ""; //$NON-NLS-1$
    }

}
