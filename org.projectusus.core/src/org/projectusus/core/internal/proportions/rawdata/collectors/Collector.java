package org.projectusus.core.internal.proportions.rawdata.collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.projectusus.core.IMetricsWriter;

public abstract class Collector extends ASTVisitor {

    protected IFile file;
    protected IMetricsWriter metricsWriter;

    public Collector() {
        super();
    }

    // public Collector( IFile file ) {
    // super();
    // this.file = file;
    // }

    public void setup( IFile newFile, IMetricsWriter newMetricsWriter ) {
        this.file = newFile;
        this.metricsWriter = newMetricsWriter;
    }

    protected IMetricsWriter getMetricsWriter() {
        return metricsWriter;
    }
}
