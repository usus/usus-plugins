// Copyright (c) 2005-2006, 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.yellowcount;

import java.util.ArrayList;
import java.util.List;

class WorkspaceCount {
    private final List<ProjectCount> projectCounts = new ArrayList<ProjectCount>();

    void add( ProjectCount projectCount ) {
        if( !projectCount.isEmpty() ) {
            projectCounts.add( projectCount );
        }
    }

    int sum() {
        int result = 0;
        for( ProjectCount projectCount : projectCounts ) {
            result += projectCount.sum();
        }
        return result;
    }

    int size() {
        return projectCounts.size();
    }
}
