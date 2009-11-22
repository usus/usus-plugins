package org.projectusus.ui.internal.proportions.actions;

import static org.projectusus.core.internal.proportions.UsusModel.getUsusModel;
import static org.projectusus.core.internal.util.UsusPreferenceKeys.AUTO_COMPUTE;
import static org.projectusus.ui.internal.util.UITexts.toggleAutoComplete_name;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.action.Action;
import org.projectusus.core.internal.UsusCorePlugin;

public class ToggleAutoCompute extends Action {

    public ToggleAutoCompute() {
        super( toggleAutoComplete_name, AS_CHECK_BOX );
        init();
    }

    @Override
    public void run() {
        getUsusModel().setAutoCompute( isChecked() );
    }

    private void init() {
        IEclipsePreferences prefs = UsusCorePlugin.getDefault().getPreferences();
        setChecked( prefs.getBoolean( AUTO_COMPUTE, true ) );
    }
}
