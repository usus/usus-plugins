package org.projectusus.core.internal.proportions.rawdata.jdtdriver;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.projectusus.core.internal.proportions.rawdata.IFileRawData;
import org.projectusus.core.internal.proportions.rawdata.JDTSupport;

public abstract class MetricsCollector extends ASTVisitor {

    private final IFile file;

    public MetricsCollector( IFile file ) {
        super();
        this.file = file;
    }

    protected IFileRawData getFileRawData() {
        return JDTSupport.getFileRawDataFor( file );
    }
}
