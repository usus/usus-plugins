package org.projectusus.core.internal.proportions.model;

import org.eclipse.core.resources.IFile;

public class MetricKGHotspot implements IMetricKGHotspot {

    private final String className;
    private final int classSize;
    private IFile file;
    private final int sourcePosition;

    public MetricKGHotspot( String className, int classSize, int sourcePosition ) {
        super();
        this.className = className;
        this.classSize = classSize;
        this.sourcePosition = sourcePosition;
    }

    public void setFile( IFile file ) {
        this.file = file;
    }

    public int getHotness() {
        return classSize;
    }

    public String getClassName() {
        return className;
    }

    public int getClassSize() {
        return classSize;
    }

    public IFile getFile() {
        return file;
    }

    public int getSourcePosition() {
        return sourcePosition;
    }

    @Override
    public String toString() {
        return getClassName() + " (KG = " + getClassSize() + ")"; //$NON-NLS-1$//$NON-NLS-2$ 
    }
}
