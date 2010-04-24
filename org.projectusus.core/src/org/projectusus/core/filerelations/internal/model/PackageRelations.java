package org.projectusus.core.filerelations.internal.model;

import java.util.HashSet;
import java.util.Set;

import org.projectusus.core.filerelations.model.FileRelation;
import org.projectusus.core.filerelations.model.PackageRelation;
import org.projectusus.core.filerelations.model.Packagename;

public class PackageRelations extends Relations<Packagename, PackageRelation> {

    private final FileRelations fileRelations;

    public PackageRelations( FileRelations fileRelations ) {
        this.fileRelations = fileRelations;
        calcPackageRelations();
    }

    private void calcPackageRelations() {
        for( FileRelation fileRelation : fileRelations.getAllDirectRelations() ) {
            if( fileRelation.isCrossPackage() ) {
                Packagename source = fileRelation.getSourcePackage();
                Packagename target = fileRelation.getTargetPackage();
                this.add( new PackageRelation( source, target ), source, target );
            }
        }
    }

    public Set<Packagename> getDirectPackageRelationsFrom( Packagename packagename ) {
        Set<Packagename> descriptors = new HashSet<Packagename>();
        for( PackageRelation relation : this.getDirectRelationsFrom( packagename ) ) {
            descriptors.add( relation.getTarget() );
        }
        return descriptors;
    }

}
