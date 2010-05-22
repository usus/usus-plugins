// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.bugprison;

import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;

import java.util.Arrays;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.swt.widgets.Composite;
import org.projectusus.bugprison.core.IAverageMetrics;
import org.projectusus.ui.internal.viewer.ColumnDescLabelProvider;
import org.projectusus.ui.internal.viewer.IColumnDesc;
import org.projectusus.ui.internal.viewer.TableViewPart;

public class AverageCodeProportionsView extends TableViewPart<IAverageMetrics> {

    @Override
    protected IColumnDesc<IAverageMetrics>[] getColumns() {
        return AverageMetricsColumns.values();
    }

    @Override
    protected IStructuredContentProvider getContentProvider() {
        return new AverageMetricsCp();
    }

    @Override
    protected ColumnDescLabelProvider<IAverageMetrics> getLabelProvider() {
        return new AverageMetricsLP( Arrays.asList( getColumns() ) );
    }

    @Override
    public void createPartControl( Composite parent ) {
        createViewer( parent );
        refresh();
    }

    private void refresh() {
        IWorkspaceRoot input = getWorkspace().getRoot();
        viewer.setInput( input );
    }

}
