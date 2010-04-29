package org.projectusus.core.filerelations;

import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.jgrapht.alg.StrongConnectivityInspector;
import org.projectusus.core.filerelations.internal.metrics.ACDCalculator;
import org.projectusus.core.filerelations.internal.metrics.BottleneckCalculator;
import org.projectusus.core.filerelations.internal.model.FileRelations;
import org.projectusus.core.filerelations.internal.model.PackageCycles;
import org.projectusus.core.filerelations.internal.model.PackageRelations;
import org.projectusus.core.filerelations.internal.model.RelationGraph;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.FileRelation;
import org.projectusus.core.filerelations.model.PackageRelation;
import org.projectusus.core.filerelations.model.Packagename;

public class FileRelationMetrics {

    private final FileRelations relations;
    private PackageRelations packageRelations;

    public FileRelationMetrics() {
        super();
        this.relations = new FileRelations();
        initPackageRelations();
    }

    public void addFileRelation( ClassDescriptor source, ClassDescriptor target ) {
        relations.add( new FileRelation( source, target ) );
        initPackageRelations();
    }

    public void handleFileRemoval( IFile file ) {
        ClassDescriptor.removeAllClassesIn( file );
        relations.markAndRemoveAllRelationsStartingAt( file );
        relations.registerAllRelationsEndingAt( file );
        initPackageRelations();
    }

    public List<FileRelation> findRelationsThatNeedRepair() {
        return relations.extractRelationsRegisteredForRepair();
    }

    public int getCCD( ClassDescriptor descriptor ) {
        return new ACDCalculator( relations ).getCCD( descriptor );
    }

    public int getBottleneckCount( ClassDescriptor descriptor ) {
        return new BottleneckCalculator( relations ).getBottleneckCount( descriptor );
    }

    public Set<ClassDescriptor> getChildren( ClassDescriptor descriptor ) {
        return relations.getDirectRelationsFrom( descriptor );
    }

    public Set<ClassDescriptor> getAllClassDescriptors() {
        return ClassDescriptor.getAll();
    }

    public Set<Packagename> getAllPackages() {
        return Packagename.getAll();
    }

    public void addClass( ITypeBinding binding ) {
        ClassDescriptor.of( binding );
    }

    public void remove( FileRelation relation ) {
        relations.remove( relation );
        initPackageRelations();
    }

    public int getTransitiveParentCount( ClassDescriptor clazz ) {
        return getTransitiveRelationsTo( clazz ).size();
    }

    private Set<FileRelation> getTransitiveRelationsTo( ClassDescriptor clazz ) {
        return relations.getTransitiveRelationsTo( clazz.getFile(), clazz.getClassname() );
    }

    public Set<Packagename> getChildren( Packagename packagename ) {
        return packageRelations.getDirectPackageRelationsFrom( packagename );
    }

    public PackageCycles getPackageCycles() {
        RelationGraph<Packagename, PackageRelation> graph = new RelationGraph<Packagename, PackageRelation>( packageRelations );
        StrongConnectivityInspector<Packagename, PackageRelation> inspector = new StrongConnectivityInspector<Packagename, PackageRelation>( graph );
        return new PackageCycles( inspector.stronglyConnectedSets() );
    }

    private void initPackageRelations() {
        packageRelations = new PackageRelations( relations );
    }

}
