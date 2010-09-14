// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.autotestsuite.ui.internal;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.ui.JavaElementLabelProvider;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.projectusus.autotestsuite.core.internal.AllJavaProjectsInWorkspace;
import org.projectusus.autotestsuite.core.internal.IAllJavaProjects;
import org.projectusus.autotestsuite.core.internal.RequiredJavaProjects;

class CheckedProjectsViewer extends CheckboxTableViewer {

    CheckedProjectsViewer( Composite parent ) {
        super( new Table( parent, SWT.CHECK | SWT.BORDER ) );
        getControl().setLayoutData( new GridData( GridData.FILL_BOTH ) );
        setContentProvider( new ArrayContentProvider() );
        setLabelProvider( new JavaElementLabelProvider( JavaElementLabelProvider.SHOW_DEFAULT ) );
        setInput( new AllJavaProjectsInWorkspace().find() );
    }

    void updateCheckedProjects( IJavaProject root ) {
        Object[] checked = getCheckedElements();
        Set<IJavaProject> projects = new LinkedHashSet<IJavaProject>();
        if( root != null ) {
            IAllJavaProjects allProjects = new AllJavaProjectsInWorkspace();
            projects.addAll( new RequiredJavaProjects( allProjects ).findFor( root ) );
        }
        setInput( projects );
        setCheckedElements( checked );
    }
}