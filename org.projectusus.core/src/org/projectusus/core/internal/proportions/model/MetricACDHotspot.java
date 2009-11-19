package org.projectusus.core.internal.proportions.model;

public class MetricACDHotspot extends Hotspot implements IMetricACDHotspot {

    private final String className;
    private final int classCCD;

    public MetricACDHotspot( String className, int classCCD, int sourcePosition ) {
        super( classCCD, sourcePosition );
        this.className = className;
        this.classCCD = classCCD;
    }

    public String getClassName() {
        return className;
    }

    public int getClassCCD() {
        return classCCD;
    }

    @Override
    public String toString() {
        return getClassName() + " (ACD = " + getClassCCD() + ")"; //$NON-NLS-1$//$NON-NLS-2$ 
    }
}
