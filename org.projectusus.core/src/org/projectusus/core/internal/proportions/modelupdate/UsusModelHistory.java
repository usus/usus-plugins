// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.modelupdate;

import static java.util.Collections.unmodifiableList;
import static org.projectusus.core.internal.proportions.modelupdate.IUsusModelUpdate.Type.COMPUTATION_RUN;
import static org.projectusus.core.internal.proportions.modelupdate.IUsusModelUpdate.Type.TEST_RUN;

import java.util.ArrayList;
import java.util.List;

import org.projectusus.core.internal.proportions.modelupdate.IUsusModelUpdate.Type;

public class UsusModelHistory implements IUsusModelHistory {

    private final Checkpoints checkpoints = new Checkpoints();
    private final List<IUsusModelUpdate> history = new ArrayList<IUsusModelUpdate>();
    private UsusModelStatus status = new UsusModelStatus();

    public void add( IUsusModelUpdate modelUpdate ) {
        checkpoints.createCheckpoint( status, modelUpdate.getEntries() );
        history.add( modelUpdate );
        updateStatus( modelUpdate );
    }

    public IUsusModelStatus getLastStatus() {
        return status;
    }

    public List<ICheckpoint> getCheckpoints() {
        return unmodifiableList( checkpoints.getCheckpoints() );
    }

    private void updateStatus( IUsusModelUpdate modelUpdate ) {
        status = new UsusModelStatus( findLast( COMPUTATION_RUN ), findLast( TEST_RUN ) );
    }

    private IUsusModelUpdate findLast( Type type ) {
        IUsusModelUpdate result = null;
        for( IUsusModelUpdate historyElement : history ) {
            if( historyElement.getType() == type ) {
                result = historyElement;
            }
        }
        return result;
    }
}
