package org.projectusus.core.internal.proportions.rawdata;

import static org.projectusus.core.internal.proportions.rawdata.ContractUtil.fullString;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.projectusus.c4j.C4JFileWriter;
import org.projectusus.c4j.UsusContractBase;
import org.projectusus.core.IMetricsResultVisitor;
import org.projectusus.core.filerelations.model.ASTNodeHelper;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.WrappedTypeBinding;

public class MetricsAccessorContract extends UsusContractBase<MetricsAccessor> {

    public MetricsAccessorContract( MetricsAccessor target ) {
        super( target );
    }

    public void classInvariant() {
        // TODO no class invariant identified yet
    }

    public void pre_MetricsAccessor( ASTNodeHelper converter ) {
        // TODO no pre-condition identified yet
    }

    public void post_MetricsAccessor( ASTNodeHelper converter ) {
        // TODO no post-condition identified yet
    }

    public void pre_acceptAndGuide( IMetricsResultVisitor visitor ) {
        // TODO Auto-generated pre-condition
        assertThat( visitor != null, "visitor_not_null" );
    }

    public void post_acceptAndGuide( IMetricsResultVisitor visitor ) {
        // TODO no post-condition identified yet
    }

    public void pre_dropRawData( IProject project ) {
        // TODO Auto-generated pre-condition
        assertThat( project != null, "project_not_null" );
    }

    public void post_dropRawData( IProject project ) {
        // TODO no post-condition identified yet
    }

    public void pre_dropRawData( IFile file ) {
        // TODO Auto-generated pre-condition
        assertThat( file != null, "file_not_null" );
    }

    public void post_dropRawData( IFile file ) {
        // TODO no post-condition identified yet
    }

    public void pre_cleanupRelations( IProgressMonitor monitor ) {
        // TODO Auto-generated pre-condition
        assertThat( monitor != null, "monitor_not_null" );
    }

    public void post_cleanupRelations( IProgressMonitor monitor ) {
        // TODO no post-condition identified yet
    }

    public static void pre_addClassReference( WrappedTypeBinding sourceType, WrappedTypeBinding targetType ) {
        // TODO Auto-generated pre-condition
        C4JFileWriter.assertStatic( sourceType != null, "sourceType_not_null" );
        C4JFileWriter.assertStatic( targetType != null, "targetType_not_null" );
        C4JFileWriter.assertStatic( sourceType.isValid(), "Wrapped source type must be valid" );
        C4JFileWriter.assertStatic( targetType.isValid(), "Wrapped target type must be valid" );
        C4JFileWriter.assertStatic( sourceType.getUnderlyingResource() != null, "Underlying Resource of source type must not be null." );
        C4JFileWriter.assertStatic( targetType.getUnderlyingResource() != null, "Underlying Resource of target type must not be null." );
        C4JFileWriter.assertStatic( !sourceType.equals( targetType ), fullString( "Source ", sourceType ) + " must not equal" + fullString( " Target ", targetType ) );
    }

    public static void post_addClassReference( WrappedTypeBinding sourceType, WrappedTypeBinding targetType ) {
        String sourceString = fullString( " Source: ", sourceType );
        String targetString = fullString( " Target: ", targetType );
        ClassDescriptor source = null;
        ClassDescriptor target = null;
        for( ClassDescriptor descriptor : ClassDescriptor.getAll() ) {
            if( descriptor.getClassname().equals( sourceType.getClassname() ) && descriptor.getFile().equals( sourceType.getUnderlyingResource() ) ) {
                source = descriptor;
            }
            if( descriptor.getClassname().equals( targetType.getClassname() ) && descriptor.getFile().equals( targetType.getUnderlyingResource() ) ) {
                target = descriptor;
            }
        }

        C4JFileWriter.assertStatic( source != null, "There is a ClassDescriptor for" + sourceString );
        C4JFileWriter.assertStatic( target != null, "There is a ClassDescriptor for" + targetString );
        C4JFileWriter.assertStatic( source.getChildren().contains( target ), "Target is a child of Source." + sourceString + targetString );
        C4JFileWriter.assertStatic( target.getParents().contains( source ), "Source is a parent of Target." + sourceString + targetString );
    }

}
