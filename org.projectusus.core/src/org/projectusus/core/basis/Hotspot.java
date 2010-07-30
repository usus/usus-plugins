// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.basis;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.eclipse.core.resources.IFile;

public class Hotspot implements Comparable<Hotspot> {

    private IFile file;
    private final int metricsValue;
    private final SourceCodeLocation location;

    public Hotspot( SourceCodeLocation location, int metricsValue, IFile file ) {
        this.location = location;
        this.metricsValue = metricsValue;
        this.file = file;
    }

    public String getName() {
        return location.getName();
    }

    public IFile getFile() {
        return file;
    }

    public int getMetricsValue() {
        return metricsValue;
    }

    public int getSourcePosition() {
        return location.getSourcePosition();
    }

    public int getLineNumber() {
        return location.getLineNumber();
    }

    @Override
    public boolean equals( Object obj ) {
        if( !(obj instanceof Hotspot) ) {
            return false;
        }
        Hotspot other = (Hotspot)obj;
        return new EqualsBuilder().append( getName(), other.getName() ).append( getFile(), other.getFile() ).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append( getName() ).append( getFile() ).toHashCode();
    }

    public int compareTo( Hotspot o ) {
        return getName().compareTo( o.getName() );
    }

    @Override
    public String toString() {
        return getName() + "-" + getMetricsValue() + "[" + super.toString() + "]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }
}
