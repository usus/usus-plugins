package org.projectusus.core.basis;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.projectusus.core.filerelations.model.Packagename;

public class PackageHotspot implements Hotspot {

    private final Packagename packagename;
    private final int metricsValue;

    public PackageHotspot( Packagename packagename, int metricsValue ) {
        this.packagename = packagename;
        this.metricsValue = metricsValue;
    }

    public int getMetricsValue() {
        return metricsValue;
    }

    public String getName() {
        return packagename.getJavaElement().getElementName();
    }

    public String getPath() {
        return packagename.getJavaElement().getPath().toOSString();
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

}
