// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.modelupdate;

import static java.util.Collections.unmodifiableList;

import java.util.List;

import org.joda.time.DateTime;
import org.projectusus.core.internal.proportions.model.CodeProportion;

/**
 * update the Usus model with results from a metrics computation.
 * 
 * @author leif
 */
public class ComputationRunModelUpdate implements IUsusModelUpdate {

    private final DateTime time;
    private final boolean computationSuccessful;
    private final List<CodeProportion> entries;

    public ComputationRunModelUpdate( List<CodeProportion> entries, boolean computationSuccessful ) {
        this.entries = entries;
        this.computationSuccessful = computationSuccessful;
        time = new DateTime();
    }

    public List<CodeProportion> getEntries() {
        return unmodifiableList( entries );
    }

    public DateTime getTime() {
        return time;
    }

    public Type getType() {
        return Type.COMPUTATION_RUN;
    }

    public boolean isSuccessful() {
        return computationSuccessful;
    }
}
