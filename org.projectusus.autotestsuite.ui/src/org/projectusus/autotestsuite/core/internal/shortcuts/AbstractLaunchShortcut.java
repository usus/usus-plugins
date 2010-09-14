package org.projectusus.autotestsuite.core.internal.shortcuts;

import static org.projectusus.autotestsuite.AutoTestSuitePlugin.logStatusOf;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.projectusus.autotestsuite.core.internal.config.ExtendedJUnitLaunchConfigurationCreator;

abstract class AbstractLaunchShortcut<T> implements ILaunchShortcut {

    public void launch( IEditorPart editor, String mode ) {
        // unsupported
    }

    public void launch( ISelection selection, String mode ) {
        try {
            T target = extract( selection );
            ILaunchConfiguration config = findOrCreateConfig( target );
            DebugUITools.launch( config, mode );
        } catch( CoreException exception ) {
            logStatusOf( exception );
        }
    }

    protected abstract T extract( ISelection selection );

    protected abstract ILaunchConfiguration findOrCreateConfig( T target ) throws CoreException;

    protected ExtendedJUnitLaunchConfigurationCreator creator() {
        return new ExtendedJUnitLaunchConfigurationCreator();
    }
}
