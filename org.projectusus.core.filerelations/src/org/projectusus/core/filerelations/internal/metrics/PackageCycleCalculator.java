package org.projectusus.core.filerelations.internal.metrics;

import java.util.Collection;

import org.jgrapht.alg.CycleDetector;
import org.projectusus.core.filerelations.internal.model.FileRelations;
import org.projectusus.core.filerelations.internal.model.RelationGraph;
import org.projectusus.core.filerelations.internal.model.Relations;
import org.projectusus.core.filerelations.model.FileRelation;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.core.filerelations.model.Relation;

public class PackageCycleCalculator {

    private final FileRelations fileRelations;

    public PackageCycleCalculator( FileRelations fileRelations ) {
        this.fileRelations = fileRelations;
    }

    public int countPackagesInCycles() {
        Collection<FileRelation> allDirectRelations = fileRelations.getAllDirectRelations();

        Relations<Packagename, Relation<Packagename>> packageRelations = new Relations<Packagename, Relation<Packagename>>();

        for( FileRelation fileRelation : allDirectRelations ) {
            if( fileRelation.isCrossPackage() ) {
                Packagename source = fileRelation.getSourcePackage();
                Packagename target = fileRelation.getTargetPackage();
                packageRelations.add( Relation.of( source, target ), source, target );
            }
        }
        RelationGraph<Packagename> graph = new RelationGraph<Packagename>( packageRelations );
        return new CycleDetector<Packagename, Relation<Packagename>>( graph ).findCycles().size();
    }
}
