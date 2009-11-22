// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.history;

import static org.projectusus.ui.internal.util.UITexts.checkpointsHistoryChart_title;
import static org.projectusus.ui.internal.util.UITexts.checkpointsHistoryChart_x;
import static org.projectusus.ui.internal.util.UITexts.checkpointsHistoryChart_y;
import static org.swtchart.IAxis.Position.Secondary;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.swtchart.Chart;
import org.swtchart.IAxis;
import org.swtchart.Range;

class CheckpointsHistoryChart extends Chart {

    CheckpointsHistoryChart( Composite parent ) {
        super( parent, SWT.NONE );
        init();
    }

    private void init() {
        getTitle().setText( checkpointsHistoryChart_title );
        getLegend().setPosition( SWT.LEFT );
        initXAxis();
        initYAxis();
    }

    private void initXAxis() {
        IAxis xAxis = getAxisSet().getXAxis( 0 );
        xAxis.getTitle().setText( checkpointsHistoryChart_x );
        xAxis.setRange( new Range( 0, 16 ) );
    }

    private void initYAxis() {
        IAxis yAxis = getAxisSet().getYAxis( 0 );
        yAxis.getTitle().setText( checkpointsHistoryChart_y );
        yAxis.setRange( new Range( -5, 105 ) );
        yAxis.setPosition( Secondary );
    }
}
