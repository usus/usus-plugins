package org.projectusus.ui.internal.bugreport;

import org.eclipse.jdt.core.IMethod;
import org.projectusus.core.bugreport.Bug;
import org.projectusus.core.bugreport.BugList;
import org.projectusus.core.bugreport.IBuggyProject;

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
