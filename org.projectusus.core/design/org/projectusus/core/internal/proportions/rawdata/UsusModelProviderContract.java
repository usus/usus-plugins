package org.projectusus.core.internal.proportions.rawdata;

import java.util.Set;

import org.projectusus.c4j.UsusContractBase;
import org.projectusus.core.IMetricsResultVisitor;
import org.projectusus.core.IMetricsWriter;
import org.projectusus.core.IUsusModel;
import org.projectusus.core.IUsusModelForAdapter;
import org.projectusus.core.basis.GraphNode;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.WrappedTypeBinding;
import org.projectusus.core.internal.proportions.rawdata.UsusModelProvider;

public class UsusModelProviderContract extends UsusContractBase<UsusModelProvider> {

    public UsusModelProviderContract( UsusModelProvider target ) {
        super( target );
    }

    public void classInvariant() {
        // TODO no class invariant identified yet
    }

    public static void pre_ususModel() {
        // TODO no pre-condition identified yet
    }

    public static void post_ususModel() {
        IUsusModel returnValue = (IUsusModel)getReturnValue();
        // TODO no post-condition identified yet
    }

    public static void pre_getMetricsWriter() {
        // TODO no pre-condition identified yet
    }

    public static void post_getMetricsWriter() {
        IMetricsWriter returnValue = (IMetricsWriter)getReturnValue();
        // TODO no post-condition identified yet
    }

    public static void pre_ususModelForAdapter() {
        // TODO no pre-condition identified yet
    }

    public static void post_ususModelForAdapter() {
        IUsusModelForAdapter returnValue = (IUsusModelForAdapter)getReturnValue();
        // TODO no post-condition identified yet
    }

    public static void pre_getAllClassRepresenters() {
        // TODO no pre-condition identified yet
    }

    public static void post_getAllClassRepresenters() {
        Set<GraphNode> returnValue = (Set<GraphNode>)getReturnValue();
        // TODO no post-condition identified yet
    }

    public static void pre_getAllPackages() {
        // TODO no pre-condition identified yet
    }

    public static void post_getAllPackages() {
        Set<GraphNode> returnValue = (Set<GraphNode>)getReturnValue();
        // TODO no post-condition identified yet
    }

    public static void pre_getAllCrossPackageClasses() {
        // TODO no pre-condition identified yet
    }

    public static void post_getAllCrossPackageClasses() {
        Set<GraphNode> returnValue = (Set<GraphNode>)getReturnValue();
        // TODO no post-condition identified yet
    }

    public static void pre_addClassReference( WrappedTypeBinding sourceType, WrappedTypeBinding targetType ) {
        // TODO Auto-generated pre-condition
        assertStatic( sourceType != null, "sourceType_not_null" );
        assertStatic( targetType != null, "targetType_not_null" );
        assertStatic( sourceType.isValid(), "Wrapped source type must be valid" );
        assertStatic( targetType.isValid(), "Wrapped target type must be valid" );
        assertStatic( sourceType.getUnderlyingResource() != null, "Underlying Resource of source type must not be null." );
        assertStatic( targetType.getUnderlyingResource() != null, "Underlying Resource of target type must not be null." );
        assertStatic( !sourceType.equals( targetType ), fullString( "Source ", sourceType ) + " must not equal" + fullString( " Target ", targetType ) );
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

        assertStatic( source != null, "There is a ClassDescriptor for" + sourceString );
        assertStatic( target != null, "There is a ClassDescriptor for" + targetString );
        assertStatic( source.getChildren().contains( target ), "Target is a child of Source." + sourceString + targetString );
        assertStatic( target.getParents().contains( source ), "Source is a parent of Target." + sourceString + targetString );
    }

    private static String fullString( String label, WrappedTypeBinding type ) {
        return label + type.getPackagename() + " " + type.getClassname();
    }

    public static void pre_acceptAndGuide( IMetricsResultVisitor visitor ) {
        // TODO Auto-generated pre-condition
        assertStatic( visitor != null, "visitor_not_null" );
    }

    public static void post_acceptAndGuide( IMetricsResultVisitor visitor ) {
        // TODO no post-condition identified yet
    }

}
