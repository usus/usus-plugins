// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.bugreport;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class MethodLocation {

    private String project = ""; //$NON-NLS-1$
    private String className = ""; //$NON-NLS-1$
    private String packageName = ""; //$NON-NLS-1$
    private String methodName = ""; //$NON-NLS-1$

    public String getProject() {
        return project;
    }

    public String getClassName() {
        return className;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setProject( String project ) {
        this.project = project;
    }

    public void setClassName( String className ) {
        this.className = className;
    }

    public void setPackageName( String packageName ) {
        this.packageName = packageName;
    }

    public void setMethodName( String methodName ) {
        this.methodName = methodName;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append( project ).append( packageName ).append( className ).append( methodName ).toHashCode();
    }

    @Override
    public boolean equals( Object obj ) {
        if( !(obj instanceof MethodLocation) ) {
            return false;
        }
        MethodLocation other = (MethodLocation)obj;
        return new EqualsBuilder().append( getProject(), other.getProject() ).append( getPackageName(), other.getPackageName() ).append( getClassName(), other.getClassName() )
                .append( methodName, other.getMethodName() ).isEquals();
    }
}
