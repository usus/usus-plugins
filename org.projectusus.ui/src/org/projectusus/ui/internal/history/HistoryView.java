// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.history;

import static org.swtchart.ISeries.SeriesType.LINE;

import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.ViewPart;
import org.projectusus.core.internal.proportions.CodeProportion;
import org.projectusus.core.internal.proportions.IUsusModel;
import org.projectusus.core.internal.proportions.IUsusModelListener;
import org.projectusus.core.internal.proportions.IUsusModelStatus;
import org.projectusus.core.internal.proportions.IsisMetrics;
import org.projectusus.core.internal.proportions.UsusModel;
import org.projectusus.core.internal.proportions.checkpoints.ICheckpoint;
import org.swtchart.Chart;
import org.swtchart.ILineSeries;
import org.swtchart.ISeriesSet;
import org.swtchart.ILineSeries.PlotSymbolType;

public class HistoryView extends ViewPart {

    private Chart chart;

    @Override
    public void createPartControl( Composite parent ) {
        chart = new CheckpointsHistoryChart( parent );
        refresh();
        getModel().addUsusModelListener( new IUsusModelListener() {
            public void ususModelChanged( IUsusModelStatus lastStatus, List<CodeProportion> entries ) {
                Display.getDefault().asyncExec( new Runnable() {
                    public void run() {
                        refresh();
                    }
                } );
            }
        } );
    }

    @Override
    public void setFocus() {
        if( chart != null && !chart.isDisposed() ) {
            chart.setFocus();
        }
    }

    private void refresh() {
        for( IsisMetrics metric : IsisMetrics.values() ) {
            updateSeries( metric );
        }
        chart.redraw();
    }

    private void updateSeries( IsisMetrics metric ) {
        ISeriesSet seriesSet = chart.getSeriesSet();
        cleanOldValues( seriesSet, metric );
        createSeries( seriesSet, metric, getValues( metric ) );
    }

    private double[] getValues( IsisMetrics metric ) {
        List<ICheckpoint> checkpoints = getModel().getCheckpoints();
        return new Checkpoints2GraphicsConverter( checkpoints ).get( metric );
    }

    private void createSeries( ISeriesSet seriesSet, IsisMetrics metric, double[] values ) {
        String seriesId = metric.toString();
        ILineSeries series = (ILineSeries)seriesSet.createSeries( LINE, seriesId );
        series.setYSeries( values );
        series.setSymbolType( PlotSymbolType.DIAMOND );
        series.setSymbolSize( 2 );
    }

    private IUsusModel getModel() {
        return UsusModel.getInstance();
    }

    private void cleanOldValues( ISeriesSet seriesSet, IsisMetrics metric ) {
        if( seriesSet.getSeries( metric.toString() ) != null ) {
            seriesSet.deleteSeries( metric.toString() );
        }
    }
}
