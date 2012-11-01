package org.projectusus.core;

import java.util.Set;

import net.sourceforge.c4j.ContractBase;

import org.projectusus.core.basis.GraphNode;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.WrappedTypeBinding;

public class UsusModelProviderContract extends ContractBase<UsusModelProvider> {

    public UsusModelProviderContract( UsusModelProvider target ) {
        super( target );
    }

    public void classInvariant() {
        // TODO no class invariant identified yet
    }

    public static void pre_ususModel() {
        // TODO no pre-condition identified yet
        // assert false : "C4J ist aktiviert!";
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
        assert sourceType != null : "sourceType_not_null";
        assert targetType != null : "targetType_not_null";
        assert sourceType.isValid() : "Wrapped source type must be valid";
        assert targetType.isValid() : "Wrapped target type must be valid";
        assert sourceType.getUnderlyingResource() != null : "Underlying Resource of source type must not be null.";
        assert targetType.getUnderlyingResource() != null : "Underlying Resource of target type must not be null.";
        assert !sourceType.equals( targetType ) : fullString( "Source ", sourceType ) + " must not equal" + fullString( " Target ", targetType );
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

        assert source != null : "There is a ClassDescriptor for" + sourceString;
        assert target != null : "There is a ClassDescriptor for" + targetString;
        assert source.getChildren().contains( target ) : "Target is a child of Source." + sourceString + targetString;
        assert target.getParents().contains( source ) : "Source is a parent of Target." + sourceString + targetString;
    }

    private static String fullString( String label, WrappedTypeBinding type ) {
        return label + type.getPackagename() + " " + type.getClassname();
    }

    public static void pre_acceptAndGuide( IMetricsResultVisitor visitor ) {
        // TODO Auto-generated pre-condition
        assert visitor != null : "visitor_not_null";
    }

    public static void post_acceptAndGuide( IMetricsResultVisitor visitor ) {
        // TODO no post-condition identified yet
    }

}
