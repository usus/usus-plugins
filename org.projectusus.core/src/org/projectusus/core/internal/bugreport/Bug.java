// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.bugreport;

import java.io.Serializable;

import org.joda.time.DateTime;

public class Bug implements Serializable {

    private static final long serialVersionUID = -4147451314568950190L;

    private String project = ""; //$NON-NLS-1$
    private String title = ""; //$NON-NLS-1$
    private String className = ""; //$NON-NLS-1$
    private String packageName = ""; //$NON-NLS-1$
    private String methodName = ""; //$NON-NLS-1$
    private BugMetrics bugMetrics = new BugMetrics();
    private DateTime reportTime = new DateTime();
    private DateTime creationTime = new DateTime();

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName( String className ) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName( String methodName ) {
        this.methodName = methodName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName( String packageName ) {
        this.packageName = packageName;
    }

    public BugMetrics getBugMetrics() {
        return bugMetrics;
    }

    public void setBugMetrics( BugMetrics bugMetrics ) {
        this.bugMetrics = bugMetrics;
    }

    public String getProject() {
        return project;
    }

    public void setProject( String javaProject ) {
        this.project = javaProject;
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

}
