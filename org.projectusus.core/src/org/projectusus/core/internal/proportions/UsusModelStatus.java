// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

import static org.eclipse.osgi.util.NLS.bind;
import static org.projectusus.core.internal.util.CoreTexts.ususModelStatus_info;
import static org.projectusus.core.internal.util.CoreTexts.ususModelStatus_ok;
import static org.projectusus.core.internal.util.CoreTexts.ususModelStatus_stale;

import java.util.Date;

class UsusModelStatus implements IUsusModelStatus {

    private Date lastComputerRun = new Date();
    private Date lastTestRun = null;
    private boolean lastComputationRunSuccessful = false;

    void setLastComputationRunSuccessful( boolean lastComputationRunSuccessful ) {
        this.lastComputationRunSuccessful = lastComputationRunSuccessful;
    }

    void updateLastComputerRun() {
        this.lastComputerRun = new Date();
    }

    void updateLastTestRun() {
        this.lastTestRun = new Date();
    }

    @Override
    public String toString() {
        return (isStale() ? ususModelStatus_stale : ususModelStatus_ok) + " - " + renderInfo(); //$NON-NLS-1$
    }

    // interface methods
    // //////////////////

    public Date getLastComputerRun() {
        return lastComputerRun;
    }

    public Date getLastTestRun() {
        return lastTestRun;
    }

    public boolean isLastComputationRunSuccessful() {
        return lastComputationRunSuccessful;
    }

    public boolean isStale() {
        boolean result = true;
        if( lastTestRun != null ) {
            result = lastComputationRunSuccessful && lastTestRun.before( lastComputerRun );
        }
        return result;
    }

    // internal
    // /////////

    private String renderInfo() {
        return bind( ususModelStatus_info, getLastComputerRun(), getLastTestRun() );
    }
}
