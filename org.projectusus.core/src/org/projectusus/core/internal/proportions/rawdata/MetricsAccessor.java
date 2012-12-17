package org.projectusus.core.internal.proportions.rawdata;

import java.util.Set;

import net.sourceforge.c4j.ContractReference;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.projectusus.core.IMetricsResultVisitor;
import org.projectusus.core.IMetricsWriter;
import org.projectusus.core.filerelations.model.ASTNodeHelper;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.ClassDescriptorCleanup;
import org.projectusus.core.filerelations.model.WrappedTypeBinding;

@ContractReference( contractClassName = "MetricsAccessorContract" )
public class MetricsAccessor implements IMetricsWriter {
    private final WorkspaceRawData workspaceRawData;

    public MetricsAccessor( ASTNodeHelper converter ) {
        super();
        workspaceRawData = new WorkspaceRawData( converter );
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

    public void addClassReference( WrappedTypeBinding source, WrappedTypeBinding target ) {
        ClassDescriptor.of( source ).addChild( ClassDescriptor.of( target ) );
    }

}
