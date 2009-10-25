// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.modelupdate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.projectusus.core.internal.proportions.CodeProportion;

public class Checkpoints {

    List<ICheckpoint> checkpoints = new ArrayList<ICheckpoint>();

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

    private static class Checkpoint implements ICheckpoint {
        private final Date time;
        private final List<CodeProportion> entries;

        Checkpoint( List<CodeProportion> entries ) {
            this.time = new Date();
            this.entries = entries;
        }

        public Date getTime() {
            return time;
        }

        public List<CodeProportion> getEntries() {
            return entries;
        }

        @Override
        public String toString() {
            return "Checkpoint " + time + " (" + entries.size() + " entries)"; //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
        }
    }
}
