package org.projectusus.core.filerelations;

import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.projectusus.core.filerelations.internal.metrics.ACDCalculator;
import org.projectusus.core.filerelations.internal.metrics.BottleneckCalculator;
import org.projectusus.core.filerelations.internal.model.FileRelations;
import org.projectusus.core.filerelations.internal.model.PackageRelations;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.FileRelation;
import org.projectusus.core.filerelations.model.Packagename;

public class FileRelationMetrics {

    private final FileRelations relations;

    public FileRelationMetrics() {
        super();
        this.relations = new FileRelations();
    }

    public void addFileRelation( ClassDescriptor source, ClassDescriptor target ) {
        relations.add( FileRelation.of( source, target ) );
    }

    public void handleFileRemoval( IFile file ) {
        ClassDescriptor.removeAllClassesIn( file );
        relations.markAndRemoveAllRelationsStartingAt( file );
        relations.registerAllRelationsEndingAt( file );
    }

    public List<FileRelation> findRelationsThatNeedRepair() {
        return relations.extractRelationsRegisteredForRepair();
    }

    public int getCCD( ClassDescriptor descriptor ) {
        return new ACDCalculator( relations ).getCCD( descriptor );
    }

    public double getRelativeACD() {
        return new ACDCalculator( relations ).getRelativeACD();
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

    public void remove( FileRelation relation ) {
        relations.remove( relation );
    }

    public int getTransitiveParentCount( ClassDescriptor descriptor ) {
        return getTransitiveRelationsTo( descriptor ).size();
    }

    private Set<FileRelation> getTransitiveRelationsTo( ClassDescriptor descriptor ) {
        return relations.getTransitiveRelationsTo( descriptor );
    }

    public PackageRelations getPackageRelations() {
        return new PackageRelations( relations );
    }

}
