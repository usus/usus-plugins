// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.autotestsuite.ui.internal.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.projectusus.autotestsuite.core.internal.ITestContainer;
import org.projectusus.autotestsuite.core.internal.ITestContainerGraph;

class TestCaseAnalysisCP implements ITreeContentProvider {

    public Object[] getElements( Object inputElement ) {
        Object[] result = new Object[0];
        if( inputElement instanceof ITestContainerGraph ) {
            ITestContainerGraph graph = (ITestContainerGraph)inputElement;
            result = graph.getRootContainers().toArray();
        }
        return result;
    }

    public Object[] getChildren( Object parentElement ) {
        List<Object> result = new ArrayList<Object>();
        if( parentElement instanceof ITestContainer ) {
            ITestContainer container = (ITestContainer)parentElement;
            result.addAll( container.getTestContainers() );
            result.addAll( container.getTestCases() );
        }
        return result.toArray();
    }

    public Object getParent( Object element ) {
        return null;
    }

    public boolean hasChildren( Object element ) {
        return getChildren( element ).length > 0;
    }

    public void dispose() {
        // unused
    }

    public void inputChanged( Viewer viewer, Object oldInput, Object newInput ) {
        // unused
    }
}
