package org.projectusus.core.internal.proportions.rawdata.jdtdriver;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.projectusus.core.internal.proportions.rawdata.IFileRawData;
import org.projectusus.core.internal.proportions.rawdata.IProjectRawData;

public abstract class MetricsCollector extends ASTVisitor {

    protected final IFile file;

    public MetricsCollector( IFile file ) {
        super();
        this.file = file;
    }

    protected IFileRawData getFileRawData() {
        IProjectRawData projectRawData = (IProjectRawData)file.getProject().getAdapter( IProjectRawData.class );
        return projectRawData.getFileRawData( file );
    }
}
