package org.projectusus.ui.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.projectusus.core.basis.Hotspot;

import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

class DisplayHotspotCreator {

    private final Set<Hotspot> oldHotspots;
    private final Set<Hotspot> currentHotspots;

    public DisplayHotspotCreator( List<Hotspot> oldHotspots, List<Hotspot> currentHotspots ) {
        super();
        this.oldHotspots = new HashSet<Hotspot>( oldHotspots );
        this.currentHotspots = new HashSet<Hotspot>( currentHotspots );
    }

    public List<DisplayHotspot> hotspots() {
        Set<DisplayHotspot> result = new HashSet<DisplayHotspot>();
        SetView<Hotspot> oldNotInNew = Sets.difference( oldHotspots, currentHotspots );
        for( Hotspot hotspot : oldNotInNew ) {
            result.add( new DisplayHotspot( hotspot, null ) );
        }
        SetView<Hotspot> newNotInOld = Sets.difference( currentHotspots, oldHotspots );
        for( Hotspot hotspot : newNotInOld ) {
            result.add( new DisplayHotspot( null, hotspot ) );
        }
        SetView<Hotspot> oldAlsoInNew = Sets.difference( oldHotspots, oldNotInNew );
        SetView<Hotspot> newAlsoInOld = Sets.difference( currentHotspots, newNotInOld );

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
            result.add( new DisplayHotspot( oldHotspot, newHotspot ) );
        }

        ArrayList<DisplayHotspot> resultList = new ArrayList<DisplayHotspot>( result );
        Collections.sort( resultList );
        return resultList;
    }
}
