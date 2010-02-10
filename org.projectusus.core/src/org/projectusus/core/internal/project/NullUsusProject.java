// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.project;

import org.eclipse.jdt.core.IMethod;
import org.projectusus.core.internal.bugreport.Bug;
import org.projectusus.core.internal.bugreport.BugList;

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

    public String getProjectName() {
        return ""; //$NON-NLS-1$
    }

    public BugList getBugsFor( IMethod method ) {
        return new BugList();
    }

}
