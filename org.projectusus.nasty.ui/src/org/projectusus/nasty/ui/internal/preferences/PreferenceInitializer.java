// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.nasty.ui.internal.preferences;

import static org.projectusus.nasty.ui.internal.NastyUsusPreferenceKey.NASTY_MODE;
import static org.projectusus.nasty.ui.internal.NastyUsusPreferenceKey.NUCLEAR_STRIKE;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.projectusus.nasty.ui.internal.NastyUsus;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

    @Override
    public void initializeDefaultPreferences() {
        IPreferenceStore store = NastyUsus.getDefault().getPreferenceStore();
        // switch the global nastiness off initially, but the individual items
        // on, so if someone switches the global option on, he'll get them all
        store.setDefault( NASTY_MODE.name(), false);
        store.setDefault( NUCLEAR_STRIKE.name(), true);
    }
}
