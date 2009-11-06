// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.modelupdate;

import java.util.List;

import org.projectusus.core.internal.proportions.model.CodeProportion;
import org.projectusus.core.internal.proportions.modelupdate.checkpoints.Checkpoint;

class Checkpoints {

    private final List<ICheckpoint> checkpoints;

    Checkpoints( List<ICheckpoint> checkpoints ) {
        this.checkpoints = checkpoints;
    }

    void createCheckpoint( List<CodeProportion> entries ) {
        checkpoints.add( new Checkpoint( entries ) );
    }

    List<ICheckpoint> getCheckpoints() {
        return checkpoints;
    }

    @Override
    public String toString() {
        return "Checkpoints (" + checkpoints.size() + ")"; //$NON-NLS-1$//$NON-NLS-2$
    }
}
