package org.projectusus.core.internal.proportions.rawdata.collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.projectusus.core.internal.proportions.rawdata.IFileRawData;
import org.projectusus.core.internal.proportions.rawdata.JDTSupport;

public abstract class Collector extends ASTVisitor {

    private final IFile file;

    public Collector( IFile file ) {
        super();
        this.file = file;
    }

    protected IFileRawData getFileRawData() {
        return JDTSupport.getFileRawDataFor( file );
    }
}
