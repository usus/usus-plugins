// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.history;

import static org.eclipse.swt.SWT.COLOR_BLACK;
import static org.eclipse.swt.SWT.COLOR_BLUE;
import static org.eclipse.swt.SWT.COLOR_GRAY;
import static org.eclipse.swt.SWT.COLOR_GREEN;
import static org.eclipse.swt.SWT.COLOR_MAGENTA;
import static org.eclipse.swt.SWT.COLOR_RED;
import static org.eclipse.swt.SWT.COLOR_YELLOW;
import static org.projectusus.core.internal.proportions.UsusModel.getUsusModel;
import static org.projectusus.core.internal.proportions.sqi.IsisMetrics.ACD;
import static org.projectusus.core.internal.proportions.sqi.IsisMetrics.CC;
import static org.projectusus.core.internal.proportions.sqi.IsisMetrics.CW;
import static org.projectusus.core.internal.proportions.sqi.IsisMetrics.KG;
import static org.projectusus.core.internal.proportions.sqi.IsisMetrics.ML;
import static org.projectusus.core.internal.proportions.sqi.IsisMetrics.PC;
import static org.projectusus.core.internal.proportions.sqi.IsisMetrics.TA;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.ViewPart;
import org.projectusus.core.internal.proportions.IUsusModelListener;
import org.projectusus.core.internal.proportions.model.IUsusElement;
import org.projectusus.core.internal.proportions.modelupdate.ICheckpoint;
import org.projectusus.core.internal.proportions.modelupdate.IUsusModelHistory;
import org.projectusus.core.internal.proportions.sqi.IsisMetrics;
import org.swtchart.Chart;
import org.swtchart.ILineSeries;
import org.swtchart.ISeriesSet;
import org.swtchart.ILineSeries.PlotSymbolType;
import org.swtchart.ISeries.SeriesType;

public class HistoryView extends ViewPart {

    private final Map<IsisMetrics, Color> colors = initColors();
    private Chart chart;

    @Override
    public void createPartControl( Composite parent ) {
        chart = new CheckpointsHistoryChart( parent );
        refresh();
        getUsusModel().addUsusModelListener( new IUsusModelListener() {
            public void ususModelChanged( IUsusModelHistory history, List<IUsusElement> entries ) {
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
        List<ICheckpoint> checkpoints = getUsusModel().getHistory().getCheckpoints();
        return new Checkpoints2GraphicsConverter( checkpoints ).get( metric );
    }

    private void createSeries( ISeriesSet seriesSet, IsisMetrics metric, double[] values ) {
        String seriesId = metric.toString();
        ILineSeries series = (ILineSeries)seriesSet.createSeries( SeriesType.LINE, seriesId );
        series.setYSeries( values );
        series.setSymbolType( PlotSymbolType.DIAMOND );
        series.setSymbolSize( 2 );
        series.setLineColor( colors.get( metric ) );
    }

    private void cleanOldValues( ISeriesSet seriesSet, IsisMetrics metric ) {
        if( seriesSet.getSeries( metric.toString() ) != null ) {
            seriesSet.deleteSeries( metric.toString() );
        }
    }

    private Map<IsisMetrics, Color> initColors() {
        Map<IsisMetrics, Color> result = new HashMap<IsisMetrics, Color>();
        result.put( TA, getColor( COLOR_GREEN ) );
        result.put( CC, getColor( COLOR_BLUE ) );
        result.put( PC, getColor( COLOR_BLACK ) );
        result.put( ACD, getColor( COLOR_MAGENTA ) );
        result.put( KG, getColor( COLOR_RED ) );
        result.put( ML, getColor( COLOR_GRAY ) );
        result.put( CW, getColor( COLOR_YELLOW ) );
        return result;
    }

    private Color getColor( int swtColorKey ) {
        return Display.getDefault().getSystemColor( swtColorKey );
    }
}
