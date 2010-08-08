package org.projectusus.core.filerelations.internal.metrics;

import org.jgrapht.alg.CycleDetector;
import org.projectusus.core.filerelations.model.PackageRelations;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.core.filerelations.model.Relation;
import org.projectusus.core.filerelations.model.RelationGraph;

public class PackageCycleCalculator {

    private final PackageRelations packageRelations;

    public PackageCycleCalculator( PackageRelations relations ) {
        this.packageRelations = relations;
    }

    public int countPackagesInCycles() {
        RelationGraph<Packagename> graph = new RelationGraph<Packagename>( packageRelations );
        return new CycleDetector<Packagename, Relation<Packagename>>( graph ).findCycles().size();
    }
}
