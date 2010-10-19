package org.projectusus.core.metrics;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.projectusus.core.CollectibleExtension;
import org.projectusus.core.IMetricsWriter;

public abstract class MetricsCollector extends ASTVisitor implements CollectibleExtension {

    public final static String EXTENSION_POINT_ID = "org.projectusus.core.metrics"; //$NON-NLS-1$

    private IFile file;
    private IMetricsWriter metricsWriter;

    public MetricsCollector() {
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
