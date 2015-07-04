package org.projectusus.core.filerelations.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.jgrapht.alg.StrongConnectivityInspector;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.primitives.Ints;

public class PackageRelations extends Relations<Packagename> {

    private PackageCycles packageCycles;

    private int maxLinkCount = -1;

    public PackageRelations() {
        calcPackageRelations();
        calcPackageCycles();
    }

    private void calcPackageRelations() {
        for( ClassDescriptor source : ClassDescriptor.getAll() ) {
            for( ClassDescriptor target : source.getChildrenInOtherPackages() ) {
                this.add( source.getPackagename(), target.getPackagename() );
            }
        }
    }

    private void calcPackageCycles() {
        RelationGraph<Packagename> graph = new RelationGraph<Packagename>( this );
        StrongConnectivityInspector<Packagename, Relation<Packagename>> inspector = new StrongConnectivityInspector<Packagename, Relation<Packagename>>( graph );
        packageCycles = new PackageCycles( inspector.stronglyConnectedSets() );
    }

    public Set<Packagename> getDirectPackageRelationsFrom( Packagename packagename ) {
        Set<Packagename> descriptors = new HashSet<Packagename>();
        for( Relation<Packagename> relation : this.getDirectRelationsFrom( packagename ) ) {
            descriptors.add( relation.getTarget() );
        }
        return descriptors;
    }

    public Set<Packagename> getDirectPackageRelationsTo( Packagename packagename ) {
        Set<Packagename> descriptors = new HashSet<Packagename>();
        for( Relation<Packagename> relation : this.getDirectRelationsTo( packagename ) ) {
            descriptors.add( relation.getSource() );
        }
        return descriptors;
    }

    public PackageCycles getPackageCycles() {
        return packageCycles;
    }

    public int getCrossLinkCount( Packagename source, Packagename target ) {
        int crossLinkCount = 0;
        Collection<Relation<Packagename>> relationsFromSource = getAllOutgoingRelationsFrom( source );
        for( Relation<Packagename> relation : relationsFromSource ) {
            if( relation.getTarget().equals( target ) ) {
                crossLinkCount++;
            }
        }
        return crossLinkCount;
    }

    public int getMaxCrossLinkCount() {
        if( maxCrossLinkCountCached() ) {
            return maxLinkCount;
        }

        for( Packagename sourcePackage : outgoingRelations.keys() ) {
            maxLinkCount = Math.max( maxLinkCount, calculateMaxLinkCountForPackage( sourcePackage ) );
        }
        return maxLinkCount;
    }

    private boolean maxCrossLinkCountCached() {
        return maxLinkCount > -1;
    }

    private int calculateMaxLinkCountForPackage( Packagename sourcePackage ) {
        Multimap<Packagename, Relation<Packagename>> groupedLinks = Multimaps.index( outgoingRelations.get( sourcePackage ), Relation.<Packagename> target() );

        Packagename mostTargetedPackage = findMostTargetedPackage( groupedLinks );
        int maxNumberOfRelations = groupedLinks.get( mostTargetedPackage ).size();

        return maxNumberOfRelations;
    }

    private Packagename findMostTargetedPackage( Multimap<Packagename, Relation<Packagename>> linksByPackage ) {
        List<Entry<Packagename, Relation<Packagename>>> entries = new ArrayList<Entry<Packagename, Relation<Packagename>>>( linksByPackage.entries() );
        Packagename mostTargetedPackage = Collections.max( entries, mapValuesComparator( linksByPackage ) ).getKey();
        return mostTargetedPackage;
    }

    private Comparator<Entry<Packagename, Relation<Packagename>>> mapValuesComparator( final Multimap<Packagename, Relation<Packagename>> linksByPackage ) {
        return new Comparator<Entry<Packagename, Relation<Packagename>>>() {
            public int compare( Entry<Packagename, Relation<Packagename>> e1, Entry<Packagename, Relation<Packagename>> e2 ) {
                Collection<Relation<Packagename>> relationsToPackage1 = linksByPackage.get( e1.getKey() );
                Collection<Relation<Packagename>> relationsToPackage2 = linksByPackage.get( e2.getKey() );
                return Ints.compare( relationsToPackage1.size(), relationsToPackage2.size() );
            }
        };
    }

}
