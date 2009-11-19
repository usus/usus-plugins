// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.bugreport;

import java.io.Serializable;

import org.joda.time.DateTime;

public class Bug implements Serializable {

    private static final long serialVersionUID = -4147451314568950190L;

    private String title = ""; //$NON-NLS-1$
    private MethodLocation location = new MethodLocation();
    private BugMetrics bugMetrics = new BugMetrics();
    private DateTime reportTime = new DateTime();
    private DateTime creationTime = new DateTime();

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public BugMetrics getBugMetrics() {
        return bugMetrics;
    }

    public void setBugMetrics( BugMetrics bugMetrics ) {
        this.bugMetrics = bugMetrics;
    }

    public DateTime getReportTime() {
        return reportTime;
    }

    public void setReportTime( DateTime reportTime ) {
        this.reportTime = reportTime;
    }

    public DateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime( DateTime creationTime ) {
        this.creationTime = creationTime;
    }

    public MethodLocation getLocation() {
        return location;
    }

    public void setLocation( MethodLocation location ) {
        this.location = location;
    }

}
