// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.projectsettings.ui.internal;


import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

public class ProjectSelector {

    private final Shell shell;

    public ProjectSelector( Shell shell ) {
        this.shell = shell;
    }

    public IProject selectProject( List<IProject> projects ) {
        ElementListSelectionDialog dialog = new ElementListSelectionDialog( shell, new ProjectsLabelProvider() );
        dialog.setTitle( UITexts.projectSelector_title );
        dialog.setMessage( UITexts.projectSelector_msg );
        dialog.setElements( projects.toArray( new IProject[0] ) );
        dialog.open();
        if( dialog.getReturnCode() == Window.OK ) {
            return (IProject)dialog.getFirstResult();
        }
        return null;

    }

}
