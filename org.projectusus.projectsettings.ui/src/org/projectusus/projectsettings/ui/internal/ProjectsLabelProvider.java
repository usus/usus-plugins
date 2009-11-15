// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.projectsettings.ui.internal;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.LabelProvider;

public class ProjectsLabelProvider extends LabelProvider {

    @Override
    public String getText( Object element ) {
        if( element instanceof IProject ) {
            IProject project = (IProject)element;
            return project.getName();

        }
        return ""; //$NON-NLS-1$
    }

}
