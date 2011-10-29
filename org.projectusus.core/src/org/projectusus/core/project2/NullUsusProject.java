// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.project2;


public class NullUsusProject implements IUSUSProject {

    public boolean isUsusProject() {
        return false;
    }

    public void setUsusProject( boolean ususProject ) {
        // can't make this project an usus project
    }

    public String getProjectName() {
        return ""; //$NON-NLS-1$
    }

}
