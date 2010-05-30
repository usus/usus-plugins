package org.projectusus.bugprison.core;

import org.eclipse.jdt.core.IMethod;

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
