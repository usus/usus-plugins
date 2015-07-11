package org.projectusus.ui.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.projectusus.core.basis.FileHotspot;
import org.projectusus.core.basis.Hotspot;
import org.projectusus.core.basis.PackageHotspot;
import org.projectusus.core.basis.SinglePackageHotspot;

import com.google.common.collect.Sets;

class DisplayHotspotCreator {

    private final Set<Hotspot> oldHotspots;
    private final Set<Hotspot> currentHotspots;

    public DisplayHotspotCreator( List<Hotspot> oldHotspots, List<Hotspot> currentHotspots ) {
        super();
        this.oldHotspots = new HashSet<Hotspot>( oldHotspots );
        this.currentHotspots = new HashSet<Hotspot>( currentHotspots );
    }

    public List<DisplayHotspot<?>> hotspots() {
        Set<DisplayHotspot<?>> result = new HashSet<DisplayHotspot<?>>();
        Set<Hotspot> oldNotInNew = Sets.difference( oldHotspots, currentHotspots );
        for( Hotspot hotspot : oldNotInNew ) {
            result.add( createDisplayHotspot( hotspot, null ) );
        }
        Set<Hotspot> newNotInOld = Sets.difference( currentHotspots, oldHotspots );
        for( Hotspot hotspot : newNotInOld ) {
            result.add( createDisplayHotspot( null, hotspot ) );
        }
        Set<Hotspot> oldAlsoInNew = Sets.difference( oldHotspots, oldNotInNew );
        Set<Hotspot> newAlsoInOld = Sets.difference( currentHotspots, newNotInOld );

        List<Hotspot> oldInNewSorted = new ArrayList<Hotspot>( oldAlsoInNew );
        List<Hotspot> newInOldSorted = new ArrayList<Hotspot>( newAlsoInOld );

        Collections.sort( oldInNewSorted );
        Collections.sort( newInOldSorted );

        for( int i = 0; i < oldInNewSorted.size(); i++ ) {
            Hotspot oldHotspot = oldInNewSorted.get( i );
            Hotspot newHotspot = newInOldSorted.get( i );
            if( !oldHotspot.getName().equals( newHotspot.getName() ) ) {
                continue; // TODO throw Exception?
            }
            result.add( createDisplayHotspot( oldHotspot, newHotspot ) );
        }

        List<DisplayHotspot<?>> resultList = new ArrayList<DisplayHotspot<?>>( result );
        Collections.sort( resultList );
        return resultList;
    }

    private DisplayHotspot<?> createDisplayHotspot( Hotspot oldHotspot, Hotspot newHotspot ) {
        if( oldHotspot instanceof FileHotspot || newHotspot instanceof FileHotspot ) {
            return new FileDisplayHotspot( (FileHotspot)oldHotspot, (FileHotspot)newHotspot );
        }
        if( oldHotspot instanceof SinglePackageHotspot || newHotspot instanceof SinglePackageHotspot ) {
            return new SinglePackageDisplayHotspot( (SinglePackageHotspot)oldHotspot, (SinglePackageHotspot)newHotspot );
        }
        return new PackageDisplayHotspot( (PackageHotspot)oldHotspot, (PackageHotspot)newHotspot );
    }
}
