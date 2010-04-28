package org.projectusus.ui.internal.bugreport;

import org.eclipse.jdt.core.IMethod;
import org.projectusus.core.internal.bugreport.Bug;
import org.projectusus.core.internal.bugreport.BugList;
import org.projectusus.core.internal.bugreport.IBuggyProject;

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
