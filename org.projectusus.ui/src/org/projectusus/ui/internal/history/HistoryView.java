// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.history;

import static org.projectusus.core.internal.proportions.UsusModel.getUsusModel;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.ACD;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.CC;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.CW;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.KG;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.ML;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.PC;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.TA;
import static org.projectusus.ui.internal.util.ISharedUsusColors.ISIS_METRIC_ACD;
import static org.projectusus.ui.internal.util.ISharedUsusColors.ISIS_METRIC_CC;
import static org.projectusus.ui.internal.util.ISharedUsusColors.ISIS_METRIC_CW;
import static org.projectusus.ui.internal.util.ISharedUsusColors.ISIS_METRIC_KG;
import static org.projectusus.ui.internal.util.ISharedUsusColors.ISIS_METRIC_ML;
import static org.projectusus.ui.internal.util.ISharedUsusColors.ISIS_METRIC_PC;
import static org.projectusus.ui.internal.util.ISharedUsusColors.ISIS_METRIC_TA;

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
import org.projectusus.core.internal.proportions.rawdata.CodeProportionKind;
import org.projectusus.ui.internal.util.UsusColors;
import org.swtchart.Chart;
import org.swtchart.ILineSeries;
import org.swtchart.ISeriesSet;
import org.swtchart.ILineSeries.PlotSymbolType;
import org.swtchart.ISeries.SeriesType;

public class HistoryView extends ViewPart {

    private final Map<CodeProportionKind, Color> colors = initColors();
    private Chart chart;

    @Override
    public void createPartControl( Composite parent ) {
        chart = new CheckpointsHistoryChart( parent );
        refresh();
        getUsusModel().addUsusModelListener( new IUsusModelListener() {
            public void ususModelChanged( IUsusModelHistory history, List<IUsusElement> entries ) {
                Display.getDefault().asyncExec( new Runnable() {
                    public void run() {
                        if( chart != null && !chart.isDisposed() ) {
                            refresh();
                        }
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
        for( CodeProportionKind metric : CodeProportionKind.values() ) {
            updateSeries( metric );
        }
        chart.redraw();
    }

    private void updateSeries( CodeProportionKind metric ) {
        ISeriesSet seriesSet = chart.getSeriesSet();
        cleanOldValues( seriesSet, metric );
        createSeries( seriesSet, metric, getValues( metric ) );
    }

    private double[] getValues( CodeProportionKind metric ) {
        List<ICheckpoint> checkpoints = getUsusModel().getHistory().getCheckpoints();
        return new Checkpoints2GraphicsConverter( checkpoints ).get( metric );
    }

    private void createSeries( ISeriesSet seriesSet, CodeProportionKind metric, double[] values ) {
        String seriesId = metric.toString();
        ILineSeries series = (ILineSeries)seriesSet.createSeries( SeriesType.LINE, seriesId );
        series.setYSeries( values );
        series.setSymbolType( PlotSymbolType.DIAMOND );
        series.setSymbolSize( 2 );
        series.setLineColor( colors.get( metric ) );
    }

    private void cleanOldValues( ISeriesSet seriesSet, CodeProportionKind metric ) {
        if( seriesSet.getSeries( metric.toString() ) != null ) {
            seriesSet.deleteSeries( metric.toString() );
        }
    }

    private Map<CodeProportionKind, Color> initColors() {
        Map<CodeProportionKind, Color> result = new HashMap<CodeProportionKind, Color>();
        result.put( TA, getColor( ISIS_METRIC_TA ) );
        result.put( CC, getColor( ISIS_METRIC_CC ) );
        result.put( PC, getColor( ISIS_METRIC_PC ) );
        result.put( ACD, getColor( ISIS_METRIC_ACD ) );
        result.put( KG, getColor( ISIS_METRIC_KG ) );
        result.put( ML, getColor( ISIS_METRIC_ML ) );
        result.put( CW, getColor( ISIS_METRIC_CW ) );
        return result;
    }

    private Color getColor( String key ) {
        return UsusColors.getSharedColors().getColor( key );
    }
}
