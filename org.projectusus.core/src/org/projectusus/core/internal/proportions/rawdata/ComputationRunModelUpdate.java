// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.projectusus.core.internal.proportions.model.CodeProportion;
import org.projectusus.core.internal.proportions.modelupdate.IUsusModelUpdate;

/**
 * update the Usus model with results from a metrics computation.
 * 
 * @author leif
 */
public class ComputationRunModelUpdate implements IUsusModelUpdate {

    private final DateTime time;
    private final boolean computationSuccessful;
    private List<CodeProportion> entries;

    public ComputationRunModelUpdate( List<CodeProportion> entries, boolean computationSuccessful ) {
        this.computationSuccessful = computationSuccessful;
        if( computationSuccessful ) {
            this.entries = entries;
        } else {
            this.entries = new ArrayList<CodeProportion>();
        }
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
