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
    }

    public static void post_addClassReference( WrappedTypeBinding sourceType, WrappedTypeBinding targetType ) {
        String sourceString = " Source " + sourceType.getPackagename() + " " + sourceType.getClassname();
        String targetString = " Target " + targetType.getPackagename() + " " + targetType.getClassname();
        ClassDescriptor source = null;
        ClassDescriptor target = null;
        for( ClassDescriptor descriptor : ClassDescriptor.getAll() ) {
            if( descriptor.getFile().equals( sourceType.getUnderlyingResource() ) ) {
                source = descriptor;
            }
            if( descriptor.getFile().equals( targetType.getUnderlyingResource() ) ) {
                target = descriptor;
            }
        }

        assert source != null : "There is a ClassDescriptor for" + sourceString;
        assert target != null : "There is a ClassDescriptor for" + targetString;
        if( !source.equals( target ) ) {
            assert source.getChildren().contains( target ) : "Target is a child of Source." + sourceString + targetString;
            assert target.getParents().contains( source ) : "Source is a parent of Target." + sourceString + targetString;
        }
    }

    public static void pre_acceptAndGuide( IMetricsResultVisitor visitor ) {
        // TODO Auto-generated pre-condition
        assert visitor != null : "visitor_not_null";
    }

    public static void post_acceptAndGuide( IMetricsResultVisitor visitor ) {
        // TODO no post-condition identified yet
    }

}
