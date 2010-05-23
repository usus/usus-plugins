package org.projectusus.core.filerelations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.projectusus.core.filerelations.model.FileRelation;

public class DefectFileRelations {

    private static Set<FileRelation> registeredForRepair = new HashSet<FileRelation>();

    public static void clear() {
        registeredForRepair = new HashSet<FileRelation>();
    }

    public static void registerForRepair( Set<FileRelation> relations ) {
        registeredForRepair.addAll( relations );
    }

    public static List<FileRelation> extractRelationsRegisteredForRepair() {
        List<FileRelation> relationsToRepair = new ArrayList<FileRelation>();
        for( FileRelation relation : extractRelations() ) {
            if( !relation.isObsolete() ) {
                relationsToRepair.add( relation );
            }
        }
        return relationsToRepair;
    }

    private static Set<FileRelation> extractRelations() {
        Set<FileRelation> relations = new HashSet<FileRelation>( registeredForRepair );
        registeredForRepair.clear();
        return relations;
    }

}
