package org.projectusus.core.internal.proportions.rawdata.collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.projectusus.core.UsusModelProvider;
import org.projectusus.core.internal.proportions.IMetricsWriter;

public abstract class Collector extends ASTVisitor {

    protected final IFile file;

    public Collector( IFile file ) {
        super();
        this.file = file;
    }

    protected IMetricsWriter getMetricsWriter() {
        return UsusModelProvider.getMetricsWriter();
    }
}
