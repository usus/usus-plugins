// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

import java.util.List;

import org.projectusus.core.internal.proportions.checkpoints.ICheckpoint;
import org.projectusus.core.internal.proportions.model.IUsusElement;

public interface IUsusModel {

    List<ICheckpoint> getCheckpoints();

    IUsusElement[] getElements();

    IUsusModelStatus getLastStatus();

    void addUsusModelListener( IUsusModelListener listener );

    void removeUsusModelListener( IUsusModelListener listener );

    void forceRecompute();

    void setRecomputeOnResourceChange( boolean recomputeOnResourceChange );
}
