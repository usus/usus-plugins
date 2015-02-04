package org.projectusus.ui.dependencygraph.nodes;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.ui.ISharedImages;
import org.eclipse.jdt.ui.JavaElementLabels;
import org.projectusus.core.filerelations.model.PackageRelations;
import org.projectusus.core.filerelations.model.Packagename;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

public class PackageRepresenter extends PlatformObject implements GraphNode {

    private final Packagename packagename;
    private static PackageRelations relations;

    public static Set<GraphNode> getAllPackages() {
        return transformToRepresenterSet( Packagename.getAll(), new PackageRelations() );
    }

    public static Set<GraphNode> transformToRepresenterSet( Set<Packagename> classes, final PackageRelations rel ) {
        relations = rel;
        Function<Packagename, PackageRepresenter> function = new Function<Packagename, PackageRepresenter>() {
            public PackageRepresenter apply( Packagename descriptor ) {
                return new PackageRepresenter( descriptor );
            }
        };
        return new HashSet<GraphNode>( Collections2.transform( classes, function ) );
    }

    protected PackageRepresenter( Packagename pkg ) {
        this.packagename = pkg;
    }

    public Set<? extends GraphNode> getChildren() {
        return transformToRepresenterSet( relations.getDirectPackageRelationsFrom( packagename ), relations );
    }

    public Set<? extends GraphNode> getParents() {
        return transformToRepresenterSet( relations.getDirectPackageRelationsTo( packagename ), relations );
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
        return obj instanceof PackageRepresenter && packagename.equals( ((PackageRepresenter)obj).packagename );
    }

    @Override
    public int hashCode() {
        return packagename.hashCode();
    }

    public int getFilterValue() {
        return 0;
    }

    public IFile getFile() {
        return null;
    }

    public Packagename getPackagename() {
        return packagename;
    }

    public boolean isPackageOneOf( Collection<Packagename> packages ) {
        return packages.contains( packagename );
    }

    public boolean isAtEitherEndOf( Packagename source, Packagename dest ) {
        return packagename.equals( source ) || packagename.equals( dest );
    }

    public Packagename getRelatedPackage() {
        return packagename;
    }

    public boolean isVisibleForLimitWithOtherNodes( boolean restricting, Set<GraphNode> others ) {
        if( restricting ) {
            return relations.getPackageCycles().containsPackage( packagename );
        }
        return true;
    }

    public boolean isInDifferentPackageThan( GraphNode destination ) {
        return !getRelatedPackage().equals( destination.getRelatedPackage() );
    }

    public String getDisplayText() {
        return JavaElementLabels.getTextLabel( packagename.getJavaElement(), JavaElementLabels.P_COMPRESSED );
    }

    public String getImageName() {
        return ISharedImages.IMG_OBJS_PACKAGE;
    }

    public String getTooltipText() {
        return getDisplayText();
    }

    public boolean represents( IJavaElement javaElement ) {
        IJavaElement pkg = javaElement.getAncestor( IJavaElement.PACKAGE_FRAGMENT );
        return pkg != null && getRelatedPackage().getJavaElement().getElementName().equals( pkg.getElementName() );
    }

    @Override
    public Object getAdapter( @SuppressWarnings( "rawtypes" ) Class adapter ) {
        if( adapter.equals( IJavaElement.class ) ) {
            return getPackagename().getJavaElement();
        }
        return super.getAdapter( adapter );
    }

}
