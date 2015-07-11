// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.projectsettings.ui.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class ProjectSelectionExtractor {

    private final ExecutionEvent event;

    public ProjectSelectionExtractor( ExecutionEvent event ) {
        super();
        this.event = event;
    }

    public List<IProject> getSelectedProjects() {
        List<IProject> result = new ArrayList<IProject>();
        ISelection selection = HandlerUtil.getActiveMenuSelection( event );
        if( selection instanceof IStructuredSelection ) {
            IStructuredSelection sselection = (IStructuredSelection)selection;
            List<?> elements = sselection.toList();
            for( Object object : elements ) {
                if( object instanceof IJavaProject ) {
                    IJavaProject javaProject = (IJavaProject)object;
                    result.add( javaProject.getProject() );
                }
            }
        }
        return result;
    }

}
