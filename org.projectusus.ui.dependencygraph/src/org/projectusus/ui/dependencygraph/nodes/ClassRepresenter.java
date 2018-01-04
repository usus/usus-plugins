package org.projectusus.ui.dependencygraph.nodes;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.ui.ISharedImages;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.Packagename;

public class ClassRepresenter extends PlatformObject implements GraphNode {

    private final ClassDescriptor clazz;

    public static Set<GraphNode> getAllClassRepresenters() {
        return transformToRepresenterSet( ClassDescriptor.getAll() );
    }

    public static Set<GraphNode> transformToRepresenterSet( Set<ClassDescriptor> classes ) {
        return classes.stream().map( ClassRepresenter::new ).collect( Collectors.toCollection( HashSet::new ) );
    }

    public ClassRepresenter( ClassDescriptor clazz ) {
        this.clazz = clazz;
    }

    public Set<GraphNode> getChildren() {
        return transformToRepresenterSet( clazz.getChildren() );
    }

    public Set<GraphNode> getParents() {
        return transformToRepresenterSet( clazz.getParents() );
    }

    @Override
    public boolean equals( Object obj ) {
        return obj instanceof ClassRepresenter && clazz.equals( ((ClassRepresenter)obj).clazz );
    }

    public int getFilterValue() {
        return getChildrenAndParentsInOtherPackages().size();
    }

    private Set<ClassDescriptor> getChildrenAndParentsInOtherPackages() {
        Set<ClassDescriptor> result = clazz.getChildrenInOtherPackages();
        result.addAll( clazz.getParentsInOtherPackages() );
        return result;
    }

    public boolean isVisibleForLimitWithOtherNodes( boolean restricting, Set<GraphNode> others ) {
        if( !restricting ) {
            return true;
        }
        if( getFilterValue() == 0 ) {
            return false;
        }
        for( GraphNode graphNode : others ) {
            ClassRepresenter otherRepresenter = (ClassRepresenter)graphNode;
            if( hasConnectionTo( otherRepresenter ) ) {
                return true;
            }
        }
        return false;
    }

    public boolean hasConnectionTo( ClassRepresenter otherRepresenter ) {
        return getChildrenAndParentsInOtherPackages().contains( otherRepresenter.clazz );
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
    public int hashCode() {
        return clazz.hashCode();
    }

    public boolean isPackageOneOf( Collection<Packagename> packages ) {
        return packages.contains( getPackagename() );
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

    public boolean isInDifferentPackageThan( GraphNode destination ) {
        return !getRelatedPackage().equals( destination.getRelatedPackage() );
    }

    public String getDisplayText() {
        return clazz.getClassname().toString();
    }

    public String getImageName() {
        return ISharedImages.IMG_OBJS_CLASS;
    }

    public String getTooltipText() {
        return new PackageRepresenter( getRelatedPackage() ).getTooltipText();
    }

    public boolean represents( IJavaElement javaElement ) {
        return javaElement.getResource().equals( getFile() );
    }

    @Override
    public Object getAdapter( @SuppressWarnings( "rawtypes" ) Class adapter ) {
        if( adapter.equals( IJavaElement.class ) ) {
            return JavaCore.create( getFile() );
        }
        return super.getAdapter( adapter );
    }
}
