// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.projectsettings.ui.internal;

import org.eclipse.jface.viewers.LabelProvider;
import org.projectusus.projectsettings.core.ProjectSettings;

public class SettingsLabelProvider extends LabelProvider {

    @Override
    public String getText( Object element ) {
        if( element instanceof ProjectSettings ) {
            ProjectSettings settings = (ProjectSettings)element;
            return settings.getName();

        }
        return "";
    }

}
