// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.bugreport;

import static java.util.Arrays.asList;
import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.swt.widgets.Composite;
import org.projectusus.core.internal.bugreport.Bug;
import org.projectusus.ui.internal.IColumnDesc;
import org.projectusus.ui.internal.TableViewPart;

public class BugsView extends TableViewPart<Bug> {

    @Override
    public void createPartControl( Composite parent ) {
        createViewer( parent );
        refresh();
    }

    @Override
    protected BugsContentProvider getContentProvider() {
        return new BugsContentProvider();
    }

    @Override
    protected BugsLP getLabelProvider() {
        return new BugsLP( asList( BugsColumnDesc.values() ) );
    }

    private void refresh() {
        IWorkspaceRoot input = getWorkspace().getRoot();
        viewer.setInput( input );
    }

    @Override
    protected IColumnDesc<Bug>[] getColumns() {
        return BugsColumnDesc.values();
    }

}
