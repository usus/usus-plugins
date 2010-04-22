package org.projectusus.core.filerelations.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PackagenameFactory {

    private static Map<String, Packagename> packages = new HashMap<String, Packagename>();

    public static Packagename get( String name ) {
        if( packages.containsKey( name ) ) {
            return packages.get( name );
        }
        Packagename newPackage = new Packagename( name );
        packages.put( name, newPackage );
        return newPackage;
    }

    public static Set<Packagename> getAll() {
        return new HashSet<Packagename>( packages.values() );
    }

}
