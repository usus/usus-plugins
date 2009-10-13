// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.projectsettings;

import static org.projectusus.ui.internal.util.UITexts.projectsettings_select_message;
import static org.projectusus.ui.internal.util.UITexts.projectsettings_select_title;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.projectusus.core.projectsettings.SettingsProvider;
import org.projectusus.core.projectsettings.SettingsProviderExtension;

public class SettingsProviderSelector {

    private final Shell shell;

    public SettingsProviderSelector( Shell shell ) {
        super();
        this.shell = shell;
    }

    public SettingsProvider selectSetting() {
        ElementListSelectionDialog dialog = new ElementListSelectionDialog( shell, new SettingsLabelProvider() );
        dialog.setTitle( projectsettings_select_title );
        dialog.setMessage( projectsettings_select_message );
        SettingsProvider[] providers = new SettingsProviderExtension().loadSettingsProvider().toArray( new SettingsProvider[0] );
        dialog.setElements( providers );
        dialog.open();
        if( dialog.getReturnCode() == Window.OK ) {
            return (SettingsProvider)dialog.getFirstResult();
        }
        return null;
    }

}
