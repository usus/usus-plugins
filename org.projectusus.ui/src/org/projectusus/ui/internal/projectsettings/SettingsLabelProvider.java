// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.projectsettings;

import org.eclipse.jface.viewers.LabelProvider;
import org.projectusus.core.projectsettings.SettingsProvider;

public class SettingsLabelProvider extends LabelProvider {

    @Override
    public String getText( Object element ) {
        if( element instanceof SettingsProvider ) {
            SettingsProvider settingsProvider = (SettingsProvider)element;
            return settingsProvider.getUsusProjectSettings().getName();

        }
        return ""; //$NON-NLS-1$
    }

}
