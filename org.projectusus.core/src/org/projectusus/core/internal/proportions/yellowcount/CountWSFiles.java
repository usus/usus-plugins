// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.yellowcount;

import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.projectusus.core.internal.UsusCorePlugin;

class CountWSFiles {

    private final List<IProject> projects;

    CountWSFiles( List<IProject> projects ) {
        this.projects = projects;
    }

    int compute() {
        int result = 0;
        try {
            result = countFiles( projects );
        } catch( CoreException cex ) {
            UsusCorePlugin.log( cex );
        }
        return result;
    }

    private int countFiles( List<IProject> projects ) throws CoreException {
        int result = 0;
        for( IProject project : projects ) {
            result += countFilesRec( project );
        }
        return result;
    }

    private int countFilesRec( IContainer container ) throws CoreException {
        int result = 0;
        IResource[] members = container.members();
        for( IResource resource : members ) {
            if( canTouch( resource ) && !resource.isDerived() ) {
                if( resource instanceof IContainer ) {
                    result += countFilesRec( (IContainer)resource );
                } else {
                    result += 1;
                }
            }
        }
        return result;
    }

    private boolean canTouch( IResource resource ) {
        return resource.isAccessible() && resource.isSynchronized( 1 );
    }
}
