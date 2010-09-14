package org.projectusus.core.internal.proportions.rawdata.collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.projectusus.core.IMetricsWriter;

public abstract class Collector extends ASTVisitor {

    private IFile file;
    private IMetricsWriter metricsWriter;

    public Collector() {
        super();
    }

    public void setup( IFile newFile, IMetricsWriter newMetricsWriter ) {
        this.file = newFile;
        this.metricsWriter = newMetricsWriter;
    }

    protected IFile getFile() {
        return file;
    }

    protected IMetricsWriter getMetricsWriter() {
        return metricsWriter;
    }
}
