// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.nasty.ui.internal;

import static org.projectusus.nasty.ui.internal.NastyUsusPreferenceKey.NASTY_MODE;
import static org.projectusus.nasty.ui.internal.NastyUsusPreferenceKey.NUCLEAR_STRIKE;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.projectusus.nasty.ui.internal.badthings.NuclearStrike;

class BadThingLoader implements IBadThingLoader {

    public List<IBadThingThatHappensPeriodically> loadAllPeriodic() {
        List<IBadThingThatHappensPeriodically> result = new ArrayList<IBadThingThatHappensPeriodically>();
        if( nastinessGloballyEnabled() ) {
            maybeAdd( result, new NuclearStrike(), NUCLEAR_STRIKE.name() );
        }
        return result;
    }

    private void maybeAdd( List<IBadThingThatHappensPeriodically> result, IBadThingThatHappensPeriodically badThing, String key ) {
        if( getPrefStore().getBoolean( key ) ) {
            result.add( badThing );
        }
    }

    private boolean nastinessGloballyEnabled() {
        IPreferenceStore prefStore = getPrefStore();
        return prefStore.getBoolean( NASTY_MODE.name() );
    }

    private IPreferenceStore getPrefStore() {
        return NastyUsus.getDefault().getPreferenceStore();
    }
}
