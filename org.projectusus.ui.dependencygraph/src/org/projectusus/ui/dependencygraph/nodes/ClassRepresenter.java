package org.projectusus.ui.dependencygraph.nodes;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.ui.ISharedImages;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.Packagename;

import ch.akuhn.foreach.Collect;
import ch.akuhn.foreach.ForEach;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

public class ClassRepresenter implements GraphNode {

    private final ClassDescriptor clazz;

    public static Set<GraphNode> getAllClassRepresenters() {
        return transformToRepresenterSet( ClassDescriptor.getAll() );
    }

    public static Set<GraphNode> transformToRepresenterSet( Set<ClassDescriptor> classes ) {

        // return foreachVersion( classes );

        return standardJavaVersion( classes );

        // return versionWithGoogleCollection( classes );
    }

    private static Set<GraphNode> foreachVersion( Set<ClassDescriptor> classes ) {
        for( Collect<ClassDescriptor> each : ForEach.collect( classes ) ) {
            each.yield = new ClassRepresenter( each.value );
        }
        return new HashSet<GraphNode>( ForEach.<List<GraphNode>> result() );
    }

    private static Set<GraphNode> standardJavaVersion( Set<ClassDescriptor> classes ) {
        Set<GraphNode> result = new HashSet<GraphNode>();
        for( ClassDescriptor classDescriptor : classes ) {
            result.add( new ClassRepresenter( classDescriptor ) );
        }
        return result;
    }

    private static Set<GraphNode> versionWithGoogleCollection( Set<ClassDescriptor> classes ) {
        Function<ClassDescriptor, ClassRepresenter> function = new Function<ClassDescriptor, ClassRepresenter>() {
            public ClassRepresenter apply( ClassDescriptor descriptor ) {
                return new ClassRepresenter( descriptor );
            }
        };
        return new HashSet<GraphNode>( Collections2.transform( classes, function ) );
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
}
