// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.basis;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.projectusus.core.filerelations.model.Packagename;

public class SinglePackageHotspot implements Hotspot {

    private final int metricsValue;
    private Packagename packagename;

    public SinglePackageHotspot( Packagename pkg, int metricsValue ) {
        this.packagename = pkg;
        this.metricsValue = metricsValue;
    }

    public String getName() {
        return packagename.toString();
    }

    public int getMetricsValue() {
        return metricsValue;
    }

    @Override
    public boolean equals( Object obj ) {
        if( !(obj instanceof SinglePackageHotspot) ) {
            return false;
        }
        SinglePackageHotspot other = (SinglePackageHotspot)obj;
        return new EqualsBuilder().append( getName(), other.getName() ).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append( getName() ).toHashCode();
    }

    public int compareTo( Hotspot o ) {
        return getName().compareTo( o.getName() );
    }

    @Override
    public String toString() {
        return getName() + "-" + getMetricsValue() + "[" + super.toString() + "]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    public String getPath() {
        return "";
    }
}
