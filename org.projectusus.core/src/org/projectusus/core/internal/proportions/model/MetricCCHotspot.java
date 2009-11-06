package org.projectusus.core.internal.proportions.model;

import org.eclipse.core.resources.IFile;

public class MetricCCHotspot implements IMetricCCHotspot {

    private final String className;
    private final String methodName;
    private final int cyclomaticComplexity;
    private IFile file;
    private final int sourcePosition;

    public MetricCCHotspot( String className, String methodName, int cyclomaticComplexity, int sourcePosition ) {
        super();
        this.className = className;
        this.methodName = methodName;
        this.cyclomaticComplexity = cyclomaticComplexity;
        this.sourcePosition = sourcePosition;
    }

    public int getHotness() {
        return cyclomaticComplexity;
    }

    public void setFile( IFile file ) {
        this.file = file;
    }

    public String getClassName() {
        return className;
    }

    public int getCyclomaticComplexity() {
        return cyclomaticComplexity;
    }

    public IFile getFile() {
        return file;
    }

    public String getMethodName() {
        return methodName;
    }

    public int getSourcePosition() {
        return sourcePosition;
    }

    @Override
    public String toString() {
        return getClassName() + "." + getMethodName() + " (CC = " + getCyclomaticComplexity() + ")"; //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    }
}
