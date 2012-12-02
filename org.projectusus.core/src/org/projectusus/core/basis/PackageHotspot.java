package org.projectusus.core.basis;

import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.projectusus.core.filerelations.model.Cycle;
import org.projectusus.core.filerelations.model.Packagename;

public class PackageHotspot implements Hotspot {

    private final Packagename packagename;
    private final int metricsValue;
    private final Cycle<Packagename> cycle;

    public PackageHotspot( Packagename packagename, int metricsValue, Cycle<Packagename> cycle ) {
        this.packagename = packagename;
        this.metricsValue = metricsValue;
        this.cycle = cycle;
    }

    public int getMetricsValue() {
        return metricsValue;
    }

    public String getName() {
        return packagename.getDisplayName();
    }

    public String getPath() {
        return packagename.getOSPath();
    }

    public int compareTo( Hotspot other ) {
        return getName().compareTo( other.getName() );
    }

    @Override
    public boolean equals( Object obj ) {
        if( !(obj instanceof PackageHotspot) ) {
            return false;
        }
        PackageHotspot other = (PackageHotspot)obj;
        return new EqualsBuilder().append( getName(), other.getName() ).append( getPath(), other.getPath() ).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append( getName() ).append( getPath() ).toHashCode();
    }

    public Set<Packagename> getElementsInCycle() {
        return cycle.getElementsInCycle();
    }
}
