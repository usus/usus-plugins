// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.bugreport;

import org.eclipse.jdt.core.IMethod;

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

    public static MethodLocation from( IMethod method ) {
        MethodLocation methodLocation = new MethodLocation();

        return methodLocation;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((className == null) ? 0 : className.hashCode());
        result = prime * result + ((methodName == null) ? 0 : methodName.hashCode());
        result = prime * result + ((packageName == null) ? 0 : packageName.hashCode());
        result = prime * result + ((project == null) ? 0 : project.hashCode());
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if( this == obj ) {
            return true;
        }
        if( obj == null ) {
            return false;
        }
        if( getClass() != obj.getClass() ) {
            return false;
        }
        MethodLocation other = (MethodLocation)obj;
        if( className == null ) {
            if( other.className != null ) {
                return false;
            }
        } else if( !className.equals( other.className ) ) {
            return false;
        }
        if( methodName == null ) {
            if( other.methodName != null ) {
                return false;
            }
        } else if( !methodName.equals( other.methodName ) ) {
            return false;
        }
        if( packageName == null ) {
            if( other.packageName != null ) {
                return false;
            }
        } else if( !packageName.equals( other.packageName ) ) {
            return false;
        }
        if( project == null ) {
            if( other.project != null ) {
                return false;
            }
        } else if( !project.equals( other.project ) ) {
            return false;
        }
        return true;
    }

}
