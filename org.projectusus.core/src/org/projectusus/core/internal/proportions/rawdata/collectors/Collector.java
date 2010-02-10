package org.projectusus.core.internal.proportions.rawdata.collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.dom.ASTVisitor;

public abstract class Collector extends ASTVisitor {

    protected final IFile file;

    public Collector( IFile file ) {
        super();
        this.file = file;
    }
}
