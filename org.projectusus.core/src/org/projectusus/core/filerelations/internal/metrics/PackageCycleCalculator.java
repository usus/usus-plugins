package org.projectusus.core.filerelations.internal.metrics;

import org.jgrapht.alg.CycleDetector;
import org.projectusus.core.filerelations.internal.model.FileRelations;
import org.projectusus.core.filerelations.internal.model.RelationGraph;
import org.projectusus.core.filerelations.internal.model.Relations;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.core.filerelations.model.Relation;

public class PackageCycleCalculator {

    private final FileRelations fileRelations;

    public PackageCycleCalculator( FileRelations fileRelations ) {
        this.fileRelations = fileRelations;
    }

    public int countPackagesInCycles() {
        Relations<Packagename, Relation<Packagename>> packageRelations = fileRelations.calcPackageRelations();
        RelationGraph<Packagename> graph = new RelationGraph<Packagename>( packageRelations );
        return new CycleDetector<Packagename, Relation<Packagename>>( graph ).findCycles().size();
    }
}
