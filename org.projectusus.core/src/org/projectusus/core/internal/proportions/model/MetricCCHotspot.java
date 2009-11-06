package org.projectusus.core.internal.proportions.model;


public class MetricCCHotspot extends Hotspot implements IMetricCCHotspot {

    private final String className;
    private final String methodName;
    private final int cyclomaticComplexity;

    public MetricCCHotspot( String className, String methodName, int cyclomaticComplexity, int sourcePosition ) {
        super( cyclomaticComplexity, sourcePosition );
        this.className = className;
        this.methodName = methodName;
        this.cyclomaticComplexity = cyclomaticComplexity;
    }

    public String getClassName() {
        return className;
    }

    public int getCyclomaticComplexity() {
        return cyclomaticComplexity;
    }

    public String getMethodName() {
        return methodName;
    }

    @Override
    public String toString() {
        return getClassName() + "." + getMethodName() + " (CC = " + getCyclomaticComplexity() + ")"; //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    }
}
