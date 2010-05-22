package org.projectusus.ui.internal.bugprison;

import org.eclipse.jdt.core.IMethod;
import org.projectusus.bugprison.core.Bug;
import org.projectusus.bugprison.core.BugList;
import org.projectusus.bugprison.core.IBuggyProject;

public class NullBuggyProject implements IBuggyProject {

    public BugList getBugs() {
        return new BugList();
    }

    public BugList getBugsFor( IMethod method ) {
        return new BugList();
    }

    public void saveBug( Bug bug ) {
        // does not save anything
    }

}
