// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.project;

import org.eclipse.core.resources.IProject;

public class UsusProjectSupport {

    public static boolean isUsusProject( IProject project ) {
        boolean result = false;
        if( project.isAccessible() ) {
            Object adapter = project.getAdapter( IUSUSProject.class );
            if( adapter instanceof IUSUSProject ) {
                result = ((IUSUSProject)adapter).isUsusProject();
            }
        }
        return result;
    }

    private UsusProjectSupport() {
        // static class
    }
}
