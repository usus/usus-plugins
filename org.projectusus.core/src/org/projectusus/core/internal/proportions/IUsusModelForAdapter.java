// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * provides update facilities to the Usus model.
 */
public interface IUsusModelForAdapter {

    void dropRawData( IFile file );

    void dropRawData( IProject project );

    void updateAfterComputationRun( boolean ok, IProgressMonitor monitor );

}
