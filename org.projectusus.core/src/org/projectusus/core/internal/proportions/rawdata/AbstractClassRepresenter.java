package org.projectusus.core.internal.proportions.rawdata;

import java.util.Collection;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.IJavaElement;
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
        return packages.contains( getPackagename() );
    }

    public IJavaElement getNodeJavaElement() {
        return getPackagename().getJavaElement();
    }

    public Packagename getPackagename() {
        return clazz.getPackagename();
    }

    public boolean isAtEitherEndOf( Packagename source, Packagename dest ) {
        return connects( source, clazz.getChildren(), dest ) || connects( dest, clazz.getParents(), source );
    }

    private boolean connects( Packagename classPackage, Set<ClassDescriptor> relateds, Packagename relatedPackage ) {
        if( clazz.getPackagename().equals( classPackage ) ) {
            for( ClassDescriptor related : relateds ) {
                if( related.getPackagename().equals( relatedPackage ) ) {
                    return true;
                }
            }
        }
        return false;
    }

    public Packagename getRelatedPackage() {
        return clazz.getPackagename();
    }
}
