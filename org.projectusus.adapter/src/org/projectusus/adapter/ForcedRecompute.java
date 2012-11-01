// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.adapter;

import org.eclipse.core.resources.IProject;

public class ForcedRecompute extends CodeProportionsComputerJob {

    public ForcedRecompute() {
        super( new WorkspaceCodeProportionComputationTarget() );
    }

    public ForcedRecompute( IProject project ) {
        super( new ProjectCodeProportionComputationTarget( project ) );
    }
}
