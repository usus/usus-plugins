// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

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
        return (isStale() ? "Stale (re-run test suite)" : "OK") + " - " + dump();
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

    // internal
    // /////////

    private String dump() {
        return "Last run " + getLastComputerRun() + " / Last test run " + getLastTestRun();
    }

    private boolean isStale() {
        return lastTestRun != null && lastComputationRunSuccessful && lastTestRun.before( lastComputerRun );
    }
}
