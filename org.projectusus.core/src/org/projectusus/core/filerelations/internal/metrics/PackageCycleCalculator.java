package org.projectusus.core.filerelations.internal.metrics;

import org.jgrapht.alg.CycleDetector;
import org.projectusus.core.filerelations.internal.model.PackageRelations;
import org.projectusus.core.filerelations.internal.model.RelationGraph;
import org.projectusus.core.filerelations.model.PackageRelation;
import org.projectusus.core.filerelations.model.Packagename;

public class PackageCycleCalculator {

    private final PackageRelations packageRelations;

    public PackageCycleCalculator( PackageRelations relations ) {
        this.packageRelations = relations;
    }

    public int countPackagesInCycles() {
        RelationGraph<Packagename, PackageRelation> graph = new RelationGraph<Packagename, PackageRelation>( packageRelations );
        return new CycleDetector<Packagename, PackageRelation>( graph ).findCycles().size();
    }
}
