package org.projectusus.core.statistics;

import java.util.HashSet;
import java.util.Set;

import net.sourceforge.c4j.ContractReference;

import org.projectusus.core.IMetricsResultVisitor;
import org.projectusus.core.IMetricsWriter;
import org.projectusus.core.IUsusModel;
import org.projectusus.core.IUsusModelForAdapter;
import org.projectusus.core.basis.GraphNode;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.PackageRelations;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.core.filerelations.model.WrappedTypeBinding;
import org.projectusus.core.proportions.rawdata.ClassRepresenter;
import org.projectusus.core.proportions.rawdata.PackageRepresenter;

@ContractReference( contractClassName = "UsusModelProviderContract" )
public class UsusModelProvider {

    public static IUsusModel ususModel() {
        return UsusModel.ususModel();
    }

    public static IMetricsWriter getMetricsWriter() {
        return UsusModel.ususModel().getMetricsWriter();
    }

    public static IUsusModelForAdapter ususModelForAdapter() {
        return UsusModel.ususModel();
    }

    public static Set<GraphNode> getAllClassRepresenters() {
        return ClassRepresenter.transformToRepresenterSet( ClassDescriptor.getAll() );
    }

    public static Set<GraphNode> getAllPackages() {
        return PackageRepresenter.transformToRepresenterSet( Packagename.getAll(), new PackageRelations() );
    }

    public static Set<GraphNode> getAllCrossPackageClasses() {
        Set<ClassDescriptor> crossPackageClasses = new HashSet<ClassDescriptor>();
        for( ClassDescriptor clazz : ClassDescriptor.getAll() ) {
            if( clazz.getChildrenInOtherPackages().size() > 0 ) {
                crossPackageClasses.add( clazz );
            }
        }
        return ClassRepresenter.transformToRepresenterSet( crossPackageClasses );
    }

    public static void acceptAndGuide( IMetricsResultVisitor visitor ) {
        UsusModel.ususModel().acceptAndGuide( visitor );
    }

    public static void clear() {
        UsusModel.clear();
    }

}
