package org.projectusus.core.internal.proportions.rawdata;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.projectusus.core.IMetricsAccessor;
import org.projectusus.core.IMetricsResultVisitor;
import org.projectusus.core.IMetricsWriter;
import org.projectusus.core.basis.GraphNode;
import org.projectusus.core.filerelations.model.BoundType;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.ClassDescriptorCleanup;
import org.projectusus.core.filerelations.model.CrossPackageClassRelations;
import org.projectusus.core.filerelations.model.PackageRelations;
import org.projectusus.core.filerelations.model.Packagename;

public class MetricsAccessor implements IMetricsAccessor, IMetricsWriter {
    public final WorkspaceRawData workspaceRawData;

    public MetricsAccessor() {
        super();
        workspaceRawData = new WorkspaceRawData();
    }

    public void addClassReference( BoundType sourceType, BoundType targetType ) {
        ClassDescriptor.of( sourceType ).addChild( ClassDescriptor.of( targetType ) );
    }

    public void putData( IFile file, MethodDeclaration methodDecl, String dataKey, int value ) {
        workspaceRawData.putData( file, methodDecl, dataKey, value );
    }

    public void putData( IFile file, Initializer initializer, String dataKey, int value ) {
        workspaceRawData.putData( file, initializer, dataKey, value );
    }

    public void putData( IFile file, AbstractTypeDeclaration node, String dataKey, int value ) {
        workspaceRawData.putData( file, node, dataKey, value );
    }

    public void acceptAndGuide( IMetricsResultVisitor visitor ) {
        workspaceRawData.acceptAndGuide( visitor );
    }

    public Set<GraphNode> getAllClassRepresenters() {
        return ClassRepresenter.transformToRepresenterSet( ClassDescriptor.getAll() );
    }

    public Set<GraphNode> getAllPackages() {
        return PackageRepresenter.transformToRepresenterSet( Packagename.getAll(), new PackageRelations() );
    }

    public Set<GraphNode> getAllCrossPackageClasses() {
        Set<ClassDescriptor> crossPackageClasses = new HashSet<ClassDescriptor>();
        for( ClassDescriptor clazz : ClassDescriptor.getAll() ) {
            if( clazz.getChildrenInOtherPackages().size() > 0 ) {
                crossPackageClasses.add( clazz );
            }
        }
        return CrossPackageClassRepresenter.transformToRepresenterSet( crossPackageClasses, new CrossPackageClassRelations() );
    }

    public void dropRawData( IProject project ) {
        workspaceRawData.dropRawData( project );
    }

    public void dropRawData( IFile file ) {
        workspaceRawData.dropRawData( file );
    }

    public void cleanupRelations( IProgressMonitor monitor ) {
        Set<ClassDescriptor> candidates = ClassDescriptorCleanup.extractDescriptorsRegisteredForCleanup();
        monitor.beginTask( null, candidates.size() );
        monitor.subTask( "Updating file relations" ); //$NON-NLS-1$
        for( ClassDescriptor descriptor : candidates ) {
            workspaceRawData.removeRelationIfTargetIsGone( descriptor );
            monitor.worked( 1 );
        }
        monitor.done();
    }
}
