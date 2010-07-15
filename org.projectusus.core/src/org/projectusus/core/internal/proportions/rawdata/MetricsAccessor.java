package org.projectusus.core.internal.proportions.rawdata;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.projectusus.core.IMetricsAccessor;
import org.projectusus.core.basis.GraphNode;
import org.projectusus.core.basis.YellowCountCache;
import org.projectusus.core.basis.YellowCountResult;
import org.projectusus.core.filerelations.internal.model.CrossPackageClassRelations;
import org.projectusus.core.filerelations.internal.model.PackageRelations;
import org.projectusus.core.filerelations.model.BoundType;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.ClassDescriptorCleanup;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.core.internal.proportions.IMetricsWriter;
import org.projectusus.core.internal.util.CoreTexts;
import org.projectusus.core.statistics.MetricsResultVisitor;

public class MetricsAccessor implements IMetricsAccessor, IMetricsWriter {
    private final WorkspaceRawData workspaceRawData;

    public MetricsAccessor() {
        super();
        workspaceRawData = new WorkspaceRawData();
    }

    public void dropRawData( IProject project ) {
        workspaceRawData.dropRawData( project );
    }

    public void dropRawData( IFile file ) {
        ProjectRawData projectRawData = workspaceRawData.getProjectRawData( file.getProject() );
        if( projectRawData != null ) {
            projectRawData.dropRawData( file );
        }
    }

    public void addClassReference( BoundType sourceType, BoundType targetType ) {
        ClassDescriptor.of( sourceType ).addChild( ClassDescriptor.of( targetType ) );
    }

    public void setCCValue( IFile file, MethodDeclaration methodDecl, int value ) {
        getOrCreateFileRawData( file ).setCCValue( methodDecl, value );
    }

    public void setCCValue( IFile file, Initializer initializer, int value ) {
        getOrCreateFileRawData( file ).setCCValue( initializer, value );
    }

    public void addClass( IFile file, AbstractTypeDeclaration node ) {
        getOrCreateFileRawData( file ).addClass( node );
        // TODO Aufruf dieser Methode ersetzen durch FileRawData.getOrCreateRawData
    }

    public void setMLValue( IFile file, MethodDeclaration methodDecl, int value ) {
        getOrCreateFileRawData( file ).setMLValue( methodDecl, value );
    }

    public void setMLValue( IFile file, Initializer initializer, int value ) {
        getOrCreateFileRawData( file ).setMLValue( initializer, value );
    }

    private FileRawData getOrCreateFileRawData( IFile file ) {
        return workspaceRawData.getOrCreateProjectRawData( file.getProject() ).getOrCreateFileRawData( file );
    }

    private FileRawData getFileRawData( IFile file ) {
        ProjectRawData projectRawData = workspaceRawData.getProjectRawData( file.getProject() );
        if( projectRawData == null ) {
            return null;
        }
        return projectRawData.getFileRawData( file );
    }

    public void acceptAndGuide( MetricsResultVisitor visitor ) {
        workspaceRawData.acceptAndGuide( visitor );
    }

    public Set<GraphNode> getAllClassRepresenters() {
        return ClassRepresenter.transformToRepresenterSet( ClassDescriptor.getAll() );
    }

    public Set<GraphNode> getAllPackages() {
        return PackageRepresenter.transformToRepresenterSet( Packagename.getAll(), new PackageRelations() );
    }

    public Set<GraphNode> getAllCrossPackageClasses() {
        return CrossPackageClassRepresenter.transformToRepresenterSet( ClassDescriptor.getAll(), new CrossPackageClassRelations() );
    }

    public YellowCountResult getWarnings() {
        return YellowCountCache.yellowCountCache().getResult();
    }

    public void cleanupRelations( IProgressMonitor monitor ) {
        Set<ClassDescriptor> candidates = ClassDescriptorCleanup.extractDescriptorsRegisteredForCleanup();
        monitor.beginTask( null, candidates.size() );
        monitor.subTask( CoreTexts.ususModel_UpdatingFileRelations );
        for( ClassDescriptor descriptor : candidates ) {
            removeRelationIfTargetIsGone( descriptor );
            monitor.worked( 1 );
        }
        monitor.done();
    }

    private void removeRelationIfTargetIsGone( ClassDescriptor descriptor ) {
        IFile targetFile = descriptor.getFile();
        FileRawData fileRawData = getFileRawData( targetFile );
        if( fileRawData == null ) {
            descriptor.removeFromPool();
        } else {
            ClassRawData classRawData = fileRawData.findClass( descriptor.getClassname() );
            if( classRawData == null ) {
                descriptor.removeFromPool();
            }
        }
    }

}
