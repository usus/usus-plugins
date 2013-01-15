package org.projectusus.core.internal.proportions.rawdata;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.projectusus.c4j.UsusContractBase;
import org.projectusus.core.IMetricsResultVisitor;
import org.projectusus.core.filerelations.model.ASTNodeHelper;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.WrappedTypeBinding;

public class FileRawDataContract extends UsusContractBase<FileRawData> {

    public FileRawDataContract( FileRawData target ) {
        super( target );
    }

    public void classInvariant() {
        // TODO no class invariant identified yet
    }

    public void pre_FileRawData( IFile file, ASTNodeHelper nodeHelper ) {
        // TODO Auto-generated pre-condition
        assertThat( file != null, "file_not_null" );
    }

    public void post_FileRawData( IFile file, ASTNodeHelper nodeHelper ) {
        // TODO no post-condition identified yet
    }

    public void pre_putData( WrappedTypeBinding boundType, MethodDeclaration methodDecl, String dataKey, Object value ) {
        // TODO Auto-generated pre-condition
        assertThat( methodDecl != null, "methodDecl_not_null" );
        assertThat( dataKey != null, "dataKey_not_null" );
    }

    public void post_putData( WrappedTypeBinding boundType, MethodDeclaration methodDecl, String dataKey, Object value ) {
        // TODO no post-condition identified yet
    }

    public void pre_putData( WrappedTypeBinding boundType, Initializer initializer, String dataKey, Object value ) {
        // TODO Auto-generated pre-condition
        assertThat( initializer != null, "initializer_not_null" );
        assertThat( dataKey != null, "dataKey_not_null" );
    }

    public void post_putData( WrappedTypeBinding boundType, Initializer initializer, String dataKey, Object value ) {
        // TODO no post-condition identified yet
    }

    public void pre_putData( WrappedTypeBinding boundType, AbstractTypeDeclaration node, String dataKey, Object value ) {
        // TODO Auto-generated pre-condition
        assertThat( node != null, "node_not_null" );
        assertThat( dataKey != null, "dataKey_not_null" );
    }

    public void post_putData( WrappedTypeBinding boundType, AbstractTypeDeclaration node, String dataKey, Object value ) {
        // TODO no post-condition identified yet
    }

    public void pre_dropRawData() {
        // TODO no pre-condition identified yet
    }

    public void post_dropRawData() {
        // TODO no post-condition identified yet
    }

    public void pre_acceptAndGuide( IMetricsResultVisitor visitor ) {
        // TODO Auto-generated pre-condition
        assertThat( visitor != null, "visitor_not_null" );
    }

    public void post_acceptAndGuide( IMetricsResultVisitor visitor ) {
        // TODO no post-condition identified yet
    }

    public void pre_removeRelationIfTargetIsGone( ClassDescriptor descriptor ) {
        // TODO Auto-generated pre-condition
        assertThat( descriptor != null, "descriptor_not_null" );
    }

    public void post_removeRelationIfTargetIsGone( ClassDescriptor descriptor ) {
        // TODO no post-condition identified yet
    }

}
