package org.projectusus.core.internal.proportions.rawdata;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.projectusus.core.basis.GraphNode;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.Packagename;

abstract class AbstractClassRepresenter implements GraphNode {

    protected final ClassDescriptor clazz;

    public AbstractClassRepresenter( ClassDescriptor clazz ) {
        this.clazz = clazz;
    }

    public boolean isPackage() {
        return false;
    }

    public IFile getFile() {
        return clazz.getFile();
    }

    public String getEdgeEndLabel() {
        return ""; //$NON-NLS-1$
    }

    public String getEdgeMiddleLabel() {
        return ""; //$NON-NLS-1$
    }

    public String getEdgeStartLabel() {
        return ""; //$NON-NLS-1$
    }

    @Override
    public boolean equals( Object obj ) {
        return obj instanceof AbstractClassRepresenter && clazz.equals( ((AbstractClassRepresenter)obj).clazz );
    }

    @Override
    public int hashCode() {
        return clazz.hashCode();
    }

    public boolean isPackageOneOf( Collection<Packagename> packages ) {
        return packages.contains( clazz.getPackagename() );
    }

}
