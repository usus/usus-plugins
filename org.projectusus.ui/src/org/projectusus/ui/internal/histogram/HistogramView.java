package org.projectusus.ui.internal.histogram;

import static org.projectusus.ui.colors.UsusColors.getSharedColors;
import static org.projectusus.ui.internal.AnalysisDisplayModel.displayModel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.part.ViewPart;
import org.projectusus.ui.colors.UsusColors;
import org.swtchart.Chart;
import org.swtchart.IAxis;
import org.swtchart.ILineSeries;
import org.swtchart.ISeries.SeriesType;
import org.swtchart.LineStyle;
import org.swtchart.ext.InteractiveChart;

public class HistogramView extends ViewPart {

    static final String SERIES_ID = "histogram"; //$NON-NLS-1$

    private Chart chart;
    private ChartUpdater updater;

    @Override
    public void createPartControl( Composite parent ) {
        chart = new InteractiveChart( parent, SWT.NONE );
        updater = new ChartUpdater( chart );
        initializeChart();
        hookListeners();
    }

    @Override
    public void setFocus() {
        chart.setFocus();
    }

    @Override
    public void dispose() {
        unhookListeners();
        super.dispose();
    }

    private void initializeChart() {
        ILineSeries series = (ILineSeries)chart.getSeriesSet().createSeries( SeriesType.LINE, SERIES_ID );
        series.setLineStyle( LineStyle.NONE );
        series.setSymbolColor( getSharedColors().getColor( UsusColors.USUS_LIGHT_BLUE ) );

        chart.getTitle().setVisible( false );
        chart.getLegend().setVisible( false );
        chart.setBackground( Display.getCurrent().getSystemColor( SWT.COLOR_WHITE ) );
        chart.setBackgroundInPlotArea( Display.getCurrent().getSystemColor( SWT.COLOR_WHITE ) );

        IAxis yAxis = chart.getAxisSet().getYAxis( 0 );
        yAxis.getTitle().setText( "Count" );
        yAxis.getTitle().setForeground( Display.getCurrent().getSystemColor( SWT.COLOR_BLACK ) );
        yAxis.getTick().setForeground( Display.getCurrent().getSystemColor( SWT.COLOR_DARK_GRAY ) );

        IAxis xAxis = chart.getAxisSet().getXAxis( 0 );
        xAxis.getTitle().setForeground( Display.getCurrent().getSystemColor( SWT.COLOR_BLACK ) );
        xAxis.getTick().setForeground( Display.getCurrent().getSystemColor( SWT.COLOR_DARK_GRAY ) );
    }

    private void hookListeners() {
        displayModel().addModelListener( updater );
        getSelectionService().addSelectionListener( updater );
    }

    private void unhookListeners() {
        getSelectionService().removeSelectionListener( updater );
        displayModel().removeModelListener( updater );
    }

    private ISelectionService getSelectionService() {
        return (ISelectionService)getSite().getService( ISelectionService.class );
    }
}
